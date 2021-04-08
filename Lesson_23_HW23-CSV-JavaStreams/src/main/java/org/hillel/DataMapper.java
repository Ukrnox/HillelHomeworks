package org.hillel;

public class DataMapper implements Mapper {
    @Override
    public Person apply(String s) {
        try {
            String[] line = s.split(",");
            return new Person.PersonBuilder()
                    .id(Integer.parseInt(line[0]))
                    .namePrefix(line[1])
                    .firstName(line[2])
                    .middleInitial(line[3])
                    .lastName(line[4])
                    .gender(line[5])
                    .eMail(line[6])
                    .fathersName(line[7])
                    .mothersName(line[8])
                    .mothersMaidenName(line[9])
                    .dateOfBirth(line[10])
                    .timeOfBirth(line[11])
                    .ageInYrs(Double.parseDouble(line[12]))
                    .weightInKgs(Double.parseDouble(line[13]))
                    .dateOfJoining(line[14])
                    .quarterOfJoining(line[15])
                    .halfOfJoining(line[16])
                    .yearOfJoining(line[17])
                    .monthOfJoining(line[18])
                    .monthNameOfJoining(line[19])
                    .shortMonth(line[20])
                    .dayOfJoining(line[21])
                    .dOWOfJoining(line[22])
                    .shortDOW(line[23])
                    .ageInCompanyYears(Double.parseDouble(line[24]))
                    .salary(Double.parseDouble(line[25]))
                    .lastHike(line[26])
                    .sSN(line[27])
                    .phoneNo(line[28])
                    .placeName(line[29])
                    .county(line[30])
                    .city(line[31])
                    .state(line[32])
                    .zip(line[33])
                    .region(line[34])
                    .userName(line[35])
                    .password(line[36])
                    .build();
        } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
