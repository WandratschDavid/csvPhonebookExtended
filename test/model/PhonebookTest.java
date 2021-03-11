package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


class PhonebookTest {
  Phonebook pb;
  Person p1, p2, p3, p4;
  
  
  @BeforeEach
  void setup() {
    pb = new Phonebook(new File("S:\\0pgm\\db\\phonebookTest.csv"));
    
    p1 = new Person(pb, "Widmann", "1657126517457154765");
    p2 = new Person(pb, "Mayer", "1555557154765");
    p3 = new Person(pb, "Huber", "6666666666666");
    p4 = new Person(pb, "Zemsauer", "Platz 21", "5555555555");
  }
  
  @org.junit.jupiter.api.Test
  void addPerson() {
  }
  
  @org.junit.jupiter.api.Test
  void deletePerson() {
  }
  
  @org.junit.jupiter.api.Test
  void next() {
    assertEquals(p2, pb.nextPerson(p3));
    assertEquals(p1, pb.nextPerson(p2));
    assertEquals(p4, pb.nextPerson(p1));
    assertEquals(null, pb.nextPerson(p4));
  }
  
  @org.junit.jupiter.api.Test
  void prev() {
    assertEquals(p1, pb.prevPerson(p4));
    assertEquals(p2, pb.prevPerson(p1));
    assertEquals(p3, pb.prevPerson(p2));
    assertEquals(null, pb.prevPerson(p3));
  }
  
  @Test
  void loadFromCsv() {
    File db = new File("S:\\0pgm\\db\\phonebookLoadTest.csv");
    
    pb.loadFromCsv(db);
    
    assertEquals(2, pb.size());
    assertFalse(pb.containsPerson(p1));
    assertFalse(pb.containsPerson(p2));
    assertFalse(pb.containsPerson(p3));
    assertFalse(pb.containsPerson(p4));
  }
  
  @Test
  void saveToCsv() {
    File db = new File("S:\\0pgm\\db\\phonebookSaveTest.csv");
    
    pb.saveToCsv(db);
    
    pb.deletePerson(p2);
    assertFalse(pb.containsPerson(p2));
    
    pb.loadFromCsv(db);
    
    assertTrue(pb.containsPerson(p1));
    assertTrue(pb.containsPerson(p2));
    assertTrue(pb.containsPerson(p3));
    assertTrue(pb.containsPerson(p4));
  }
  
  @Test
  void indexPerson() {
    assertEquals(1, pb.indexPerson(p3));
    assertEquals(2, pb.indexPerson(p2));
    assertEquals(3, pb.indexPerson(p1));
    assertEquals(4, pb.indexPerson(p4));
    
    Person p5 = new Person(pb, "Zzzzz","1");
    assertEquals(5,pb.indexPerson(p5));
    
    assertEquals(-1, pb.indexPerson(null));
  }
}