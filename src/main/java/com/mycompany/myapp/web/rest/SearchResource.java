package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Stage;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * REST controller for managing Stage.
 */
@RestController
@RequestMapping("/api")
public class SearchResource {

    private final Logger log = LoggerFactory.getLogger(SearchResource.class);

    @Inject
    private StageRepository stageRepository;
    @Inject
    private CompanyRepository companyRepository;
    @Inject
    private ContactRepository contactRepository;
    @Inject
    private ConventionRepository conventionRepository;
    @Inject
    private StudentRepository studentRepository;
    @Inject
    private TeacherRepository teacherRepository;
    @Inject
    private UserRepository userRepository;

    /**
     * GET  /stages : get all the stages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @RequestMapping(value = "/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Object> filterData(@RequestParam(value = "request", defaultValue = "") String request) {
        log.debug("REST request to get all Data");
        log.debug("searchPattern=" + request);

        StudentSearchItem studentsearch = new StudentSearchItem(request);
        log.debug(studentsearch.getLastname());
        List<Student> students = studentRepository.findAll();
        List<Object> res = new ArrayList<>();
        for (Student s : students) {
            boolean ok = true;
            String a = s.getFirstName();
            String b = studentsearch.getFirstname();
            if (b != null)
                if (!b.equals(a))
                    ok = false;

            a = studentsearch.getLastname();
            b = s.getLastName();
            if (ok && b != null)
                if (!b.equals(a))
                    ok = false;

            a = studentsearch.getSpeciality();
            b = s.getSpeciality();
            if (ok && a != null && b != null)
                if (!b.equals(a))
                    ok = false;

            a = studentsearch.getPhone();
            b = s.getPhone();
            if (ok && a != null && b != null)
                if (!b.equals(a))
                    ok = false;

            a = studentsearch.getMail();
            b = s.getMail();
            if (ok && a != null && b != null)
                if (!b.equals(a))
                    ok = false;

            a = studentsearch.getAddress();
            b = s.getAdress();
            if (ok && b != null)
                if (!b.equals(a))
                    ok = false;

            Date d = studentsearch.getDate();
            ZonedDateTime t = s.getBirthDate();
            Date d2 = null;
            if(t != null){
                d2 = Date.from(t.toInstant());
            }
            String op = studentsearch.getDateOpe();

            if (ok && d2 != null){
                System.out.println(op);
                System.out.println(d.toString());
                System.out.println(d2.toString());
                switch(op){
                    case"eq":
                        if(!d.equals(d2))
                            ok = false;
                        break;
                    case"infeq":
                        if(!d2.before(d))
                            ok = false;
                        break;
                    case"supeq":
                        if(!d2.after(d))
                            ok = false;
                        break;
                    case"inf":
                        if(!d2.after(d))
                            ok = false;
                        break;
                    case"sup":
                        if(!d2.after(d))
                            ok = false;
                        break;
                }
            }

            if (ok) {
                System.out.println("ok");
                res.add(s);
            }
        }
        System.out.println("res=" + res.size());
        return res;
    }


    @Timed
    public List<Stage> getAllStages() {
        log.debug("REST request to get all Data");
        List<Stage> stages = stageRepository.findAll();
        return stages;
    }

    /**
     * GET  /stages/:id : get the "id" stage.
     *
     * @param id the id of the stage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stage, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/stages/{filterCommands}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Stage> getStage(@PathVariable Long id) {
        log.debug("REST request to get Stage : {}", id);
        Stage stage = stageRepository.findOne(id);
        return Optional.ofNullable(stage)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    class StudentSearchItem {
        String firstname;
        String lastname;
        String speciality;
        String phone;
        String mail;
        String address;
        Date date;
        String dateOpe;

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getSpeciality() {
            return speciality;
        }

        public String getPhone() {
            return phone;
        }

        public String getMail() {
            return mail;
        }

        public String getAddress() {
            return address;
        }

        public Date getDate() {
            return date;
        }

        public String getDateOpe() {
            return dateOpe;
        }

        public StudentSearchItem(String request) {
            StringTokenizer st = new StringTokenizer(request, "||");
            while (st.hasMoreElements()) {
                System.out.println(st.countTokens());
                String temp = st.nextToken();
                HashMap<String, String> list = listToken(temp);
                processToken(list);
            }
        }

        private void processToken(HashMap<String, String> tokenmap) {
            String temp = "";
            temp = tokenmap.get("[field]");
            if (temp != null) {
                switch (temp) {
                    case "firstname":
                        firstname = tokenmap.get("[compareToken]");
                        System.out.println(firstname);
                        break;
                    case "lastname":
                        lastname = tokenmap.get("[compareToken]");
                        System.out.println(lastname);
                        break;
                    case "speciality":
                        speciality = tokenmap.get("[compareToken]");
                        System.out.println(speciality);
                        break;
                    case "phone":
                        phone = tokenmap.get("[compareToken]");
                        System.out.println(phone);
                        break;
                    case "mail":
                        mail = tokenmap.get("[compareToken]");
                        System.out.println(mail);
                        break;
                    case "address":
                        address = tokenmap.get("[compareToken]");
                        System.out.println(address);
                        break;
                    case "birthdate":
                        String datestr = tokenmap.get("[compareToken]");
                        dateOpe = tokenmap.get("[operator]");
                        DateFormat format = new SimpleDateFormat("MM/d/yy", Locale.ENGLISH);
                        try {
                            date = format.parse(datestr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        System.out.println(date.toString());
                        break;
                }
            }
        }

        private HashMap<String, String> listToken(String temp) {
            System.out.println(temp);
            HashMap<String, String> listFilter = new HashMap<>();
            boolean token = false;
            boolean value = false;
            String tokentemp = "";
            String valuetemp = "";
            for (int i = 0; i < temp.length(); i++) {
                char c = temp.charAt(i);
                if (c == '[') {
                    if (value) {
                        listFilter.put(tokentemp, valuetemp);
                        tokentemp = "";
                        valuetemp = "";
                    }
                    token = true;
                    value = false;

                }
                if (token) {
                    tokentemp += c;
                }
                if (value) {
                    valuetemp += c;
                }
                if (c == ']') {
                    token = false;
                    value = true;
                }
            }
            listFilter.put(tokentemp, valuetemp);
            return listFilter;
        }
    }
}
