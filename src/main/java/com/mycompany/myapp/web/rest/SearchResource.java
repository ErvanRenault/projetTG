package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.domain.search.StageSearchItem;
import com.mycompany.myapp.domain.search.StudentSearchItem;
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
        String[] tokens = request.split("\\|\\|\\|");
        log.debug("length="+tokens.length);
        List<Object> res = new ArrayList<>();
        if (tokens.length == 2) {
            log.debug(tokens[0]+" "+tokens[1]);
            switch (tokens[0]) {
                case "student":
                    StudentSearchItem studentsearch = new StudentSearchItem(tokens[1]);
                    List<Student> students = studentRepository.findAll();
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

                        a = studentsearch.getStage();
                        Stage s2 = s.getStage();
                        if (ok && s2 != null)
                            if (!(s2.getId() + "").equals(a))
                                ok = false;

                        a = studentsearch.getTeacher();
                        Teacher t2 = s.getTeacher();
                        if (ok && t2 != null)
                            if (!(t2.getId() + "").equals(a))
                                ok = false;

                        a = studentsearch.getEnquete();
                        Enquete e2 = s.getEnquete();
                        if (ok && e2 != null)
                            if (!(e2.getId() + "").equals(a))
                                ok = false;

                        a = studentsearch.getContact();
                        Contact c2 = s.getContact();
                        if (ok && c2 != null)
                            if (!(c2.getId() + "").equals(a))
                                ok = false;

                        Date d = studentsearch.getDate();
                        ZonedDateTime t = s.getBirthDate();
                        Date d2 = null;
                        if (t != null) {
                            d2 = Date.from(t.toInstant());
                        }
                        String op = studentsearch.getDateOpe();

                        if (ok && d2 != null && d != null) {
                            System.out.println(op);
                            System.out.println(d.toString());
                            System.out.println(d2.toString());
                            switch (op) {
                                case "eq":
                                    if (!d.equals(d2))
                                        ok = false;
                                    break;
                                case "infeq":
                                    if (!d2.before(d))
                                        ok = false;
                                    break;
                                case "supeq":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                                case "inf":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                                case "sup":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                            }
                        }

                        if (ok) {
                            System.out.println("ok");
                            res.add(s);
                        }
                    }
                    break;
                case "stage":
                    StageSearchItem stagesearch= new StageSearchItem(tokens[1]);
                    List<Stage> stages = stageRepository.findAll();
                    for (Stage s : stages) {
                        boolean ok = true;
                        String a = s.getAdress();
                        String b = stagesearch.getAddress();
                        if (b != null)
                            if (!b.equals(a))
                                ok = false;

                        a = stagesearch.getConvention();
                       Convention c = s.getConvention();
                        if (ok && c != null)
                            if (!(c.getId()+"").equals(a))
                                ok = false;

                        a = stagesearch.getCompany();
                        Company c1 = s.getCompany();
                        if (ok && c1 != null)
                            if (!(c1.getId()+"").equals(a))
                                ok = false;

                        a = stagesearch.getStudent();
                        Integer i1 = s.getStudent();
                        if (ok && i1 != null)
                            if (!(i1.toString()).equals(a))
                                ok = false;

                        Date d = stagesearch.getStartDate();
                        ZonedDateTime t = s.getStartDate();
                        Date d2 = null;
                        if (t != null) {
                            d2 = Date.from(t.toInstant());
                        }
                        String op = stagesearch.getStartDateOpe();

                        if (ok && d2 != null && d != null) {
                            System.out.println(op);
                            System.out.println(d.toString());
                            System.out.println(d2.toString());
                            switch (op) {
                                case "eq":
                                    if (!d.equals(d2))
                                        ok = false;
                                    break;
                                case "infeq":
                                    if (!d2.before(d))
                                        ok = false;
                                    break;
                                case "supeq":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                                case "inf":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                                case "sup":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                            }
                        }

                        d = stagesearch.getEndDate();
                        t = s.getEndDate();
                        d2 = null;
                        if (t != null) {
                            d2 = Date.from(t.toInstant());
                        }
                        op = stagesearch.getEndDateOpe();

                        if (ok && d2 != null && d != null) {
                            System.out.println(op);
                            System.out.println(d.toString());
                            System.out.println(d2.toString());
                            switch (op) {
                                case "eq":
                                    if (!d.equals(d2))
                                        ok = false;
                                    break;
                                case "infeq":
                                    if (!d2.before(d))
                                        ok = false;
                                    break;
                                case "supeq":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                                case "inf":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                                case "sup":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                            }
                        }

                        d = stagesearch.getSoonEnding();
                        t = s.getSoonEnding();
                        d2 = null;
                        if (t != null) {
                            d2 = Date.from(t.toInstant());
                        }
                        op = stagesearch.getSoonEndingOpe();

                        if (ok && d2 != null && d != null) {
                            System.out.println(op);
                            System.out.println(d.toString());
                            System.out.println(d2.toString());
                            switch (op) {
                                case "eq":
                                    if (!d.equals(d2))
                                        ok = false;
                                    break;
                                case "infeq":
                                    if (!d2.before(d))
                                        ok = false;
                                    break;
                                case "supeq":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                                case "inf":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                                case "sup":
                                    if (!d2.after(d))
                                        ok = false;
                                    break;
                            }
                        }

                        if (ok) {
                            System.out.println("ok");
                            res.add(s);
                        }
                    }
                    break;
            }

        }
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


}
