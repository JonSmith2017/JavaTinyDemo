import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lambda {
    public static void main(String[] args) {
        List<Person> roster = new ArrayList<>();
        roster.add(new Person(12, Person.Sex.MALE, "Jack0"));
        roster.add(new Person(21, Person.Sex.FEMALE, "Jack1"));
        roster.add(new Person(22, Person.Sex.FEMALE, "Jack2"));
        roster.add(new Person(25, Person.Sex.MALE, "Jack3"));
        roster.add(new Person(32, Person.Sex.MALE, "Jack4"));


        Person.printPersonsOlderThan(roster, 20);
        Person.printPersonWithinAgeRange(roster, 20, 25);
        Person.printPerson(roster, new CheckPersonForSelectiveService());
//        Person.printPerson(roster, new CheckPerson() {
//            @Override
//            public boolean test(Person person) {
//                return person.getGender() == Person.Sex.MALE
//                        && person.getAge() >= 20
//                        && person.getAge() <= 25;
//            }
//        });
        Person.printPerson(roster, (person -> person.getGender() == Person.Sex.MALE
                && person.getAge() <= 25
                && person.getAge() >= 20));

        Person.printPersonWithPredicate(roster, (person -> person.getGender() == Person.Sex.MALE
                && person.getAge() <= 25
                && person.getAge() >= 20));

        Person.processPersons(roster,
                person -> person.getGender() == Person.Sex.MALE
                        && person.getAge() >= 20
                        && person.getAge() <= 25,
                person -> person.printPerson());

        Person.processPersonsWithFunction(roster,
                person -> person.getGender() == Person.Sex.MALE
                        && person.getAge() >= 20
                        && person.getAge() <= 25,
                person -> person.getName(),
                name -> System.out.println(name));

        Person.processElements(roster,
                p -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 20
                        && p.getAge() <= 25,
                person -> person.getName(),
                s -> System.out.println(s));
    }


}

class Person {
    //    The constructors can appear anywhere in the code for the class.
//    However, by convention, most people put them before any other functions that
//    aren't constructors
    Person(int age, Sex gender, String name) {
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    public enum Sex {
        MALE, FEMALE
    }

    private int age;
    private String name;
    private Sex gender;


    int getAge() {
        return age;
    }

    Sex getGender() {
        return gender;
    }
    /*
    Suppose that you upgrade your application and change the structure of the Person class
     such that it contains different member variables; perhaps the class records and measures
     ages with a different data type or algorithm. You would have to rewrite a lot of your
     API to accommodate this change. In addition, this approach is unnecessarily restrictive;
     what if you wanted to print members younger than a certain age, for example?*/


    static void printPersonsOlderThan(List<Person> roster, int age) {
        for (Person person : roster) {
            if (person.getAge() >= age)
                person.printPerson();
        }
    }

    /*Create More Generalized Search Methods*/
    static void printPersonWithinAgeRange(List<Person> roster, int low, int high) {
        for (Person person : roster) {
            if (person.getAge() <= high
                    && person.getAge() >= low)
                person.printPerson();
        }
    }

    /*You can instead separate the code that specifies the criteria for which you want to search in a different class.*/
    static void printPerson(List<Person> roster, CheckPerson tester) {
        for (Person person : roster)
            if (tester.test(person))
                person.printPerson();
    }

    static void printPersonWithPredicate(List<Person> roster, Predicate<Person> tester) {
        for (Person person : roster)
            if (tester.test(person))
                person.printPerson();
    }

    static void processPersons(
            List<Person> roster,
            Predicate<Person> tester,
            Consumer<Person> block) {
        for (Person person : roster) {
            if (tester.test(person))
                block.accept(person);
        }
    }

    static void processPersonsWithFunction(
            List<Person> roster,
            Predicate<Person> tester,
            Function<Person, String> mapper,
            Consumer<String> block) {
        for (Person person : roster) {
            if (tester.test(person)) {
                String data = mapper.apply(person);
                block.accept(data);
            }
        }
    }

    static <X, Y> void processElements(
            Iterable<X> source,
            Predicate<X> tester,
            Function<X, Y> mapper,
            Consumer<Y> block) {
        for (X p : source)
            if (tester.test(p)) {
                Y y = mapper.apply(p);
                block.accept(y);
            }
    }


    String getName() {
        return name;
    }

    void printPerson() {
        System.out.println("name : " + name + " age  : " + age);
    }
}

interface CheckPerson {
    boolean test(Person person);
}

class CheckPersonForSelectiveService implements CheckPerson {
    public boolean test(Person person) {
        return person.getAge() <= 25
                && person.getAge() >= 21
                && person.getGender() == Person.Sex.MALE;
    }
}





