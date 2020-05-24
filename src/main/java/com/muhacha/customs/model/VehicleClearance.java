package com.muhacha.customs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicleClearances")
public class VehicleClearance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Vehicle_clearance_id")
    private long id;

    private String notes;

    @OneToMany(mappedBy = "vehicleClearance")
    private Set<Document> documents = new HashSet<>();

    private boolean isApplicationValid;

    private String make;
    private String model;
    private String year;
    private int mileage;
    private String vin;
    private String engineNumber;
    private String engineCapacity;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private Double purchasePrice;
    private Date purchaseDate;
    private Date DateOfChangeOfOwnership;
    private int yearOfManufacture;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    private Date dateCreated;

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getDateOfChangeOfOwnership() {
        return DateOfChangeOfOwnership;
    }

    public void setDateOfChangeOfOwnership(Date dateOfChangeOfOwnership) {
        DateOfChangeOfOwnership = dateOfChangeOfOwnership;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isApplicationValid() {
        return isApplicationValid;
    }

    public void setApplicationValid(boolean applicationValid) {
        isApplicationValid = applicationValid;
    }

    public VehicleClearance(Customer customer, Set<Document> documents) {
        this.customer = customer;
        //this.documents = documents;
    }


    public VehicleClearance() {
    }
}
