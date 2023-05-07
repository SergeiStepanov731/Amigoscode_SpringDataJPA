package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@mail.com", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));

            student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
            student.addBook(new Book("Think and Grow Rich", LocalDateTime.now()));
            student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));


            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);
            System.out.println("=====================================");

            student.setStudentIdCard(studentIdCard);

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 1L),
                    student,
                    new Course("CS", "IT"),
                    LocalDateTime.now()
            ));

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 2L),
                    student,
                    new Course("Spring Data JPA", "IT"),
                    LocalDateTime.now().minusDays(18)
            ));

//            student.enrolCourse(new Course("CS", "IT"));
//            student.enrolCourse(new Course("Spring Data JPA", "IT"));

            studentRepository.save(student);


            System.out.println("=====================================");
            studentRepository.findById(1L).ifPresent(s -> {
                System.out.println("fetch book lazy...");
                List<Book> books = student.getBooks();
                books.forEach(book -> {
                    System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
                });
            });
//            System.out.println("=====================================");
//            studentIdCardRepository.findById(1L).ifPresent(System.out::println);
//            System.out.println("=====================================");
//            studentRepository.deleteById(1L);
        };
    }




    private static void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@mail.com", firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55));
            studentRepository.save(student);

        }
    }
}

//    private static void sorting(StudentRepository studentRepository) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
//        Sort sort = Sort.by("firstName").ascending().and(Sort.by("age").descending());
//        studentRepository.findAll(sort).forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
//    }

//    @Bean
//    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
//        return args -> {
//
//            Student maria = new Student("Maria", "Jones", "maria.jones@mail.ru", 21);
//            Student maria2 = new Student("Maria", "Jones", "maria2.jones@mail.ru", 25);
//            Student ahmed = new Student("Ahmed", "Ali", "ahmed.ali@mail.ru", 18);
//
//            studentRepository.saveAll(List.of(maria, maria2, ahmed));
//
//            studentRepository.findStudentByEmail("ahmed.ali@mail.ru")
//                    .ifPresentOrElse(System.out::println,
//                            () -> System.out.println("Student with email is not found"));
//
//            studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual("Maria", 21).forEach(System.out::println);
//            studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqualNative("Maria", 21).forEach(System.out::println);
//
//            System.out.println("Deleting...");
//            System.out.println(studentRepository.deleteStudentById(2L));

//            System.out.print("Number of students: ");
//            System.out.println(studentRepository.count());
//
//            studentRepository.
//                    findById(2L)
//                    .ifPresentOrElse(
//                            System.out::println,
//                            () -> System.out.println("Student with id 2 is not found"));
//
//            studentRepository.
//                    findById(3L)
//                    .ifPresentOrElse(
//                            System.out::println,
//                            () -> System.out.println("Student with id 3 is not found"));

//            System.out.println("Select all students");
//            List<Student> students = studentRepository.findAll();
//            students.forEach(System.out::println);
//
//            System.out.println("Delete Maria");
//            studentRepository.deleteById(1L);
//
//            System.out.print("Number of students: ");
//            System.out.println(studentRepository.count());


