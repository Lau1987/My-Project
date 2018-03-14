/** Author: Laudro E Pineda
 * CMIS 242 Intermediate Programming
 * Professor Dinsoreanu
 * Purpose: This program will compile to manage a student database and handle variables
 *that will be used to compute a GPA.
 */

package program;


class Student {

    private double gradePoints = 0;
    private int creditHours = 0;
    private double GPA = 0;
    private String name;
    private String major;

    public Student(String name, String major) {
        this.name = name;
        this.major = major;
        this.GPA = 0;
    }

    public void courseCompleted(int cred, double gradeAsNumber) {
        this.gradePoints += (cred * gradeAsNumber);
        this.creditHours += cred;
    }

    public String getName() {
        return this.name;
    }

    public String getMajor() {
        return this.major;
    }

    public String toString() {
        return "Name: " + name + "\nMajor: " + major + "\nGPA: "
                + normalizedGPA(this.gradePoints,
                this.creditHours);
    }

    private double calculateGPA(double gradePoints, double creditHours) {
        return Math.round((((gradePoints / creditHours) * 100.0))) /
                100.0;
    }

    private double normalizedGPA(double gradePoints, double creditHours) {
        double gpa = calculateGPA(gradePoints, creditHours);
        return (this.creditHours == 0) ? 4 : gpa;
    }
}
