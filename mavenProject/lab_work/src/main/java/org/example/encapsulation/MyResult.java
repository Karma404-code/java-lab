package org.example.encapsulation;

public class MyResult {
    private int advancedJava = 8;
    private int principleOfManagement;
    int project;
    private int dataWarehouse;

    public int getPrincipleOfManagement() {
        return principleOfManagement;
    }

    public void setPrincipleOfManagement(int principleOfManagement) {
        this.principleOfManagement = principleOfManagement;
    }

    public int getDataWarehouse() {
        return dataWarehouse;
    }

    public void setDataWarehouse(int dataWarehouse) {
        this.dataWarehouse = dataWarehouse;
    }

    public int getAdvancedJava() {
        return advancedJava;
    }

    public void setAdvancedJava(int advancedJava) {

        if(advancedJava > 20) {
            System.out.println("Marks cannot be greater than 20");
        }else{
            this.advancedJava = advancedJava;
        }

    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }
}
