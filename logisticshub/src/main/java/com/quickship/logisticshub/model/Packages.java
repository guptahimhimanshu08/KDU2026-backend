package com.quickship.logisticshub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name = "books")
public class Packages {

    @Id
    private String id;

    @NotBlank(message = "destination address cannot be empty")
    private String destination;

    @NotBlank(message = "Weight cannot be empty")
    private double weight;

    @NotBlank(message = "Status cannot be empty")
    private String status; 

    @NotBlank(message = "Delivery type cannot be empty")
    private String deliveryType; 

    public Packages() {
        this.id = UUID.randomUUID().toString();
    }

    public Packages(String destination, double weight, String status, String deliveryType) {
        this();
        this.destination = destination;
        this.weight = weight;
        this.status = status;
        this.deliveryType = deliveryType;   
    }

    public String getId() { return id; }
    public String getDestination() { return destination; }
    public double getWeight() { return weight; }
    public String getStatus() { return status; }
    public String getDeliveryType() { return deliveryType; }
    
    public void setDestination(String destination) { this.destination = destination; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setStatus(String status) { this.status = status; }
    public void setDeliveryType(String deliveryType) { this.deliveryType = deliveryType; }
}