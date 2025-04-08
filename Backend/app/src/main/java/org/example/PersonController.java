package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        if (person == null) {
            log.error("getPersonById: id of person is existing");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(person);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Person>> getPersonsByColor(@PathVariable String color) {
        try {
            Color colorEnum = Color.valueOf(color.toLowerCase());
            return ResponseEntity.ok(personService.getPersonsByColor(colorEnum));
        } catch (IllegalArgumentException e) {
            log.error("getPersonsByColor: id of color cannot be found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/colors")
    public ResponseEntity<List<Color>> getColors() {
        return ResponseEntity.ok(personService.getColors());
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @GetMapping(value = "/secret", produces = org.springframework.http.MediaType.TEXT_HTML_VALUE)
    public String getEasterEgg() {
        return "<html><head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; padding: 20px; color: #333; }" +
                "h1 { color: #4CAF50; }" +
                "p { font-size: 18px; }" +
                "pre { font-family: monospace; font-size: 16px; background-color: #f4f4f4; padding: 10px; border-radius: 8px; width: fit-content; white-space: pre-wrap; word-wrap: break-word; }"
                +
                "</style>" +
                "</head><body>" +
                "<h1>Du hast das geheime Easter Egg gefunden!</h1>" +
                "<p>Hier ist dein GutscheinCode: <strong>42</strong></p>" +
                "<p>Für einen leckeren Kaffee auf Daniel Schneider's Nacken. 😁</p>" +
                "<hr>" +
                "<pre>" +
                "     (  )   (  )  )\n" +
                "     ) (   )  (  (\n" +
                "     ( )  (    ) )\n" +
                "     _____________\n" +
                "    <_____________> ___\n" +
                "    |             |/ _ \\\n" +
                "    |               | | |\n" +
                "    |               |_| |\n" +
                " ___|             |\\___/\n" +
                "/    \\___________/    \\\n" +
                "\\_____________________/" +
                "</pre>" +
                "</body></html>";
    }
}