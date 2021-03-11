package model;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * @author David Wandratsch
 * @version 1.0
 */

public class Phonebook
{
  private File db;
  private TreeSet<Person> persons;
  
  
  public Phonebook(File db) {
    this.db = db;
    persons = new TreeSet<>();
    
    loadFromCsv(db);
  }

  public void addPerson(Person person) {
    persons.add(person);
  }
  
  public void deletePerson(Person person) {
    persons.remove(person);
  }
  
  public boolean containsPerson(Person person) {
    return persons.contains(person);
  }
  
  public Person firstPerson() {
    Person first;
    try {
      first = persons.first();
    }
    catch (NoSuchElementException ex) {
      first = null;
    }
    return first;
  }
  
  public Person lastPerson() {
    Person last;
    try {
      last = persons.last();
    }
    catch (NoSuchElementException ex) {
      last = null;
    }
    return last;
  }
  
  public Person prevPerson(Person succ) {
    return persons.lower(succ);
  }
  
  public Person nextPerson(Person pred) {
    return persons.higher(pred);
  }
  
  public int indexPerson(Person person) {
    int index = -1;
    int i = 0;
    
    for (Person p : persons) {
      i++;
      if (p == person) {
        index = i;
      }
    }
    return index;
  }
  
  public int size() {
    return persons.size();
  }

  public void loadFromCsv(File file) {
    persons.clear();
    
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        persons.add(Person.fromCsvString(this, line));
      }
    }
    catch (FileNotFoundException ex) {
      // erster Start: egal, Datei wird beim save angelegt und beschrieben
    }
    catch (Exception ex) {
      System.err.println(ex.getMessage());
      ex.printStackTrace(System.err);
    }
  }
  
  public void saveToCsv(File file) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
      for (Person person : persons) {
        bw.write(person.toCsvString());
        bw.newLine();
      }
    }
    catch (Exception ex) {
      System.err.println(ex.getMessage());
      ex.printStackTrace(System.err);
    }
  }

  public void sort() {
    TreeSet<Person> neu = new TreeSet<>();
    for (Person p: persons) {
      neu.add(p);
    }
    persons = neu;
  }
}