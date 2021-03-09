package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonTest {
  Phonebook pb;
  Person p;
  Person q;
  
  @BeforeEach
  void setup() {
    pb = new Phonebook(new File("S:\\0pgm\\db\\personTest.csv"));
    
    p = new Person(pb, "Widmann", "Platz 1", "4711");
    q = new Person(pb, "Mayer", "123");
  }
  
  @Test
  void compareTo() {
    Person q = new Person(pb, "Mayer", "123");
    Person s = new Person(pb, "Widmann", "358256875628756");
    
    assertTrue(q.compareTo(p) < 0);
    assertTrue(p.compareTo(q) > 0);
    assertTrue(p.compareTo(s) == 0);
  }
  
  @Test
  void prev() {
    assertEquals(q, p.prev());
    
    Person r;
  
    r = new Person(pb, "", "");
    r.setName("Aaaa");
    assertEquals(null, r.prev());
  
    r = new Person(pb, "", "");
    r.setName("Oooo");
    assertEquals(q, r.prev());
    
    r = new Person(pb, "", "");
    r.setName("Zzzzz");
    assertEquals(p, r.prev());
  }
  
  @Test
  void next() {
    assertEquals(p, q.next());
  
    Person r;
  
    r = new Person(pb, "", "");
    r.setName("Aaaa");
    assertEquals(q, r.next());
  
    r = new Person(pb, "", "");
    r.setName("Oooo");
    assertEquals(p, r.next());
  
    r = new Person(pb, "", "");
    r.setName("Zzzzz");
    assertEquals(null, r.next());
  }
  
  @Test
  void parseFromCsv() {
    assertEquals(p, Person.fromCsvString(pb, "Widmann;Platz 1;4711"));
    assertEquals(q, Person.fromCsvString(pb, "Mayer;;123"));
    
    Person r = new Person(pb, "c","");
    assertEquals(r, Person.fromCsvString(pb, "c;;"));
  }
  
  @Test
  void saveToCsv() {
    assertEquals("Widmann;Platz 1;4711", p.toCsvString());
    assertEquals("Mayer;;123", q.toCsvString());
  }
}