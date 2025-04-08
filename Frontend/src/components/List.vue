<template>
  <div class="app">
    <input v-model="person.name" placeholder="Vorname" />
    <input v-model="person.lastName" placeholder="Nachname" />
    <input v-model="person.zipcode" placeholder="Postleitzahl" />
    <input v-model="person.city" placeholder="Stadt" />
    <input v-model="person.color" placeholder="Farbe" />
    <button @click="addPerson">Hinzufügen</button>

    <table>
      <thead>
        <tr>
          <th>Vorname</th>
          <th>Nachname</th>
          <th>Postleitzahl</th>
          <th>Stadt</th>
          <th>Farbe</th>
          <th>ID</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="person in personen" :key="person.id">
          <td>{{ person.name }}</td>
          <td>{{ person.lastName }}</td>
          <td>{{ person.zipcode }}</td>
          <td>{{ person.city }}</td>
          <td>{{ person.color }}</td>
          <td>{{ person.id }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

// Reactive state
const personen = ref([]);
const person = ref({
  name: "",
  lastName: "",
  zipcode: "",
  city: "",
  color: "",
  id: null
});

// API call to fetch persons from backend
async function fetchPersons() {
  try {
    const response = await fetch("http://localhost:8080/persons");
    if (!response.ok) throw new Error("Fehler beim Laden der Personen");
    const data = await response.json();
    personen.value = data;
  } catch (err) {
    console.error("API Fehler:", err);
  }
}

// Load data on component mount
onMounted(fetchPersons);

// Add new person (local, not saved in the backend) TODO: save person in backend later
function addPerson() {
  if (person.value.name.trim() === "" || person.value.lastName.trim() === "") return;

  // Add a new person locally (TODO: send to backend)
  const newPerson = {
    id: Date.now(),  // Simple id generation, you can replace it with real ID from the backend
    name: person.value.name,
    lastName: person.value.lastName,
    zipcode: person.value.zipcode,
    city: person.value.city,
    color: person.value.color
  };

  personen.value.push(newPerson);

  // Reset form fields
  person.value = { name: "", lastName: "", zipcode: "", city: "", color: "", id: null };
}
</script>

<style scoped>
.app {
  max-width: 800px;
  margin: 2rem auto;
  font-family: sans-serif;
}
input {
  padding: 5px;
  margin-right: 5px;
}
button {
  padding: 5px 10px;
  margin-top: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}
table th, table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
</style>
