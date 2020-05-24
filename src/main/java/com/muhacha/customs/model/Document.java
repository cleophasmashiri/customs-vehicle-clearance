package com.muhacha.customs.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "Documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String url) {
        this.name = name;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public VehicleClearance getVehicleClearance() {
        return vehicleClearance;
    }

    public void setVehicleClearance(VehicleClearance vehicleClearance) {
        this.vehicleClearance = vehicleClearance;
    }

    @ManyToOne
    @JoinColumn(name = "vehicle_clearance_id")
    @JsonBackReference
    private VehicleClearance vehicleClearance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
