package com.company.warehouse;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WarehouseAudit {
    
    public static void main(String[] args) {

        String id = "A200"; 
        // figure out other ways to do this thing
        Optional<Inventory> inventoryOptional =
        Optional.ofNullable(Inventory.findItem(id));

        Object result =
                inventoryOptional
                        .<Object>map(inv -> inv)
                        .orElseGet(ItemPlaceholder::new);


        System.out.println(result);
        
        Inventory inventory = Inventory.findItem("A100");
        Set<String> uniqueItemIds =
            inventory.getPalletItemIds()
                    .stream()          
                    .flatMap(List::stream) 
                    .collect(Collectors.toSet());
                   
        System.out.println("Unique Item IDs in Inventory: " + uniqueItemIds);
    }
}
