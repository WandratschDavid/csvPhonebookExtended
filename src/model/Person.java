package model;

/**
 * @author David Wandratsch
 * @version 1.0
 */

public class Person implements Comparable
{
    private String name;
    private String address;
    private String phone;

    private Phonebook phonebook;

    public static Person fromCsvString(Phonebook phonebook, String csv)
    {
        String[] cells = csv.split(";");

        return new Person(phonebook,
                (cells.length >= 1 && !cells[0].isEmpty() ? cells[0] : ""),
                (cells.length >= 2 && !cells[1].isEmpty() ? cells[1] : null),
                (cells.length >= 3 && !cells[2].isEmpty() ? cells[2] : ""));
    }

    public Person(Phonebook phonebook, String name, String address, String phone)
    {
        setName(name);
        setAddress(address);
        setPhone(phone);

        // automatically add person to phonebook
        setPhonebook(phonebook);
        setPhonebook(phonebook);
        phonebook.addPerson(this);
    }

    public Person(Phonebook phonebook, String name, String phone)
    {
        this(phonebook, name, null, phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Phonebook getPhonebook() {
        return phonebook;
    }

    private void setPhonebook(Phonebook phonebook) {
        this.phonebook = phonebook;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        Person other = (Person) o;

        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Person person = (Person) o;

        if (!name.equals(person.name))
        {
            return false;
        }
        if (address != null ? !address.equals(person.address) : person.address != null)
        {
            return false;
        }
        return phone.equals(person.phone);
    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + phone.hashCode();
        return result;
    }

    public Person prev()
    {
        return phonebook.prevPerson(this);
    }

    public Person next()
    {
        return phonebook.nextPerson(this);
    }

    public int index()
    {
        return phonebook.indexPerson(this);
    }

    public String toCsvString()
    {
        return name + ";" +
                (address != null ? address : "") + ";" +
                phone;
    }
}