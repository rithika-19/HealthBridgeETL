
package com.healthbridge.etl;

public class App {

    public static void main(String[] args) {

        // Synthetic HL7 v2 patient identification segment
        String hl7Message =
            "PID|||10001||SMITH^JOHN||19900115|M";

        // Split the message into fields using the pipe delimiter
        String[] fields = hl7Message.split("\\|", -1);

        // Extract patient details from their HL7 positions
        String patientId = fields[3];
        String[] name = fields[5].split("\\^");
        String lastName = name[0];
        String firstName = name[1];
        String birthDate = fields[7];
        String gender = fields[8];

        // Convert HL7 birth date to a readable format
        String formattedDate =
            birthDate.substring(0, 4) + "-" +
            birthDate.substring(4, 6) + "-" +
            birthDate.substring(6, 8);

        // Display extracted patient information
        System.out.println("=== HealthBridgeETL ===");
        System.out.println("Patient ID: " + patientId);
        System.out.println("Patient Name: " + firstName + " " + lastName);
        System.out.println("Date of Birth: " + formattedDate);
        System.out.println("Gender: " +
            (gender.equals("M") ? "Male" : "Female"));
    }
}
