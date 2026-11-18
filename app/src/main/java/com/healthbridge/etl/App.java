
package com.healthbridge.etl;

public class App {

    public static void main(String[] args) {

        // Step 1: Synthetic HL7 v2 patient data
        String hl7Message =
            "PID|||10001||SMITH^JOHN||19900115|M";

        // Step 2: Extract fields from the HL7 message
        String[] fields = hl7Message.split("\\|", -1);

        String patientId = fields[3];
        String[] name = fields[5].split("\\^");
        String lastName = name[0];
        String firstName = name[1];
        String birthDate = fields[7];
        String gender = fields[8];

        // Step 3: Convert the date to FHIR format
        String formattedDate =
            birthDate.substring(0, 4) + "-" +
            birthDate.substring(4, 6) + "-" +
            birthDate.substring(6, 8);

        // Step 4: Convert gender to FHIR terminology
        String fhirGender = switch (gender) {
            case "M" -> "male";
            case "F" -> "female";
            case "O" -> "other";
            default -> "unknown";
        };

        // Step 5: Generate a FHIR Patient JSON resource
        String fhirJson = """
            {
              "resourceType": "Patient",
              "id": "%s",
              "name": [
                {
                  "family": "%s",
                  "given": ["%s"]
                }
              ],
              "gender": "%s",
              "birthDate": "%s"
            }
            """.formatted(
                patientId,
                lastName,
                firstName,
                fhirGender,
                formattedDate
            );

        // Step 6: Display the result
        System.out.println("=== HealthBridgeETL ===");
        System.out.println("HL7 v2 to FHIR Conversion");
        System.out.println(fhirJson);
    }
}
