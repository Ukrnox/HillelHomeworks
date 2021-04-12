package org.hillel;


import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Я помню, что вы просили не пользовать lombok, но там такая борода получается из кода, что я решил поставить его;
 */

@Builder
@Value
@RequiredArgsConstructor
public class Person {
    int id;
    String namePrefix;
    String firstName;
    String middleInitial;
    String lastName;
    String gender;
    String eMail;
    String fathersName;

    String mothersName;
    String mothersMaidenName;
    String dateOfBirth;
    String timeOfBirth;

    double ageInYrs;
    double weightInKgs;
    String dateOfJoining;
    String quarterOfJoining;

    String halfOfJoining;
    String yearOfJoining;
    String monthOfJoining;
    String monthNameOfJoining;

    String shortMonth;
    String dayOfJoining;
    String dOWOfJoining;
    String shortDOW;
    double ageInCompanyYears;

    double salary;
    String lastHike;
    String sSN;
    String phoneNo;
    String placeName;
    String county;
    String city;

    String state;
    String zip;
    String region;
    String userName;
    String password;
}
