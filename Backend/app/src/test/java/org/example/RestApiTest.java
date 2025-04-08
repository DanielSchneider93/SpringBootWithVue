package org.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(classes = BewerbungsDemoApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    // testet ob das csv geladen wurde
    @Test
    public void testCsvLoading() {
        long count = personRepository.count();
        assert (count > 0);
    }

    // testet den /colors endpunkt der das color enum zurück gibt
    @Test
    public void getColors() throws Exception {
        mockMvc.perform(get("http://localhost:8080/persons/colors"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\"blau\",\"gruen\",\"violett\",\"rot\",\"gelb\",\"tuerkis\",\"weiss\"]"));
    }

    // testet ob json returned wird
    @Test
    public void testGetAllPersons() throws Exception {
        mockMvc.perform(get("http://localhost:8080/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    //testet ob ss/ß konvertierung funktioniert
    @Test
    public void testAddPerson() throws Exception {
        var personJson = "{ \"name\": \"Max\", \"lastName\": \"Muster\", \"zipcode\": \"12345\", \"city\": \"Berlin\", \"color\": \"weiss\" }";
        var person1Json = "{ \"name\": \"Anna\", \"lastName\": \"Muster\", \"zipcode\": \"54321\", \"city\": \"Berlin\", \"color\": \"weiß\" }";

        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(person1Json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/persons/color/weiss"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Max"))
                .andExpect(jsonPath("$[0].color").value("weiss"))
                .andExpect(jsonPath("$[1].name").value("Anna"))
                .andExpect(jsonPath("$[1].color").value("weiss"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetEasterEgg() throws Exception {
        mockMvc.perform(get("/persons/secret"))
                .andExpect(status().isOk())   
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Du hast das geheime Easter Egg gefunden!")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<strong>42</strong>")));  // Überprüft, ob der Kaffee gut war ;)
    }
}
