package com.quickship.logisticshub.service;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.quickship.logisticshub.advice.GlobalExceptionHandler;
import com.quickship.logisticshub.exception.InvalidPackageException;
import com.quickship.logisticshub.exception.PackageAlreadyExistsException;
import com.quickship.logisticshub.exception.PackageNotFoundException;
import com.quickship.logisticshub.model.Packages;
import com.quickship.logisticshub.repository.PackageRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class PackageService {

    private final PackageRepository repository;
    private final ExecutorService executor;
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public PackageService(PackageRepository repository, ExecutorService executor) {
        this.repository = repository;
        this.executor = executor;
    }

    @Transactional
    public Packages addPackage(Packages pkg){

        if(pkg == null){
            throw new InvalidPackageException("Package must not be null");
        }

        try{

            pkg.setStatus("PENDING");

            Packages savedPackage =  repository.save(pkg);

            executor.submit(() -> scanPackage(savedPackage.getId()));
            
            log.info("New package added successfully: id={}, destination='{}', status='{}'",
                savedPackage.getId(),
                savedPackage.getDestination(),
                savedPackage.getStatus());

                //return a 202 Accepted response
            return savedPackage;

        }catch(DataIntegrityViolationException ex){

            throw new PackageAlreadyExistsException("Package already exists", ex);
        }
    }

    private void scanPackage(String packageId) {
        try {
            Thread.sleep(3000);
            
            int id = Integer.parseInt(packageId);

            Packages pkg = repository.findById(id)
                    .orElseThrow(() -> new PackageNotFoundException("Package not found"));

            pkg.setStatus("SORTED");

            repository.save(pkg);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public Double getProjectedRevenue(){
        List<Packages> packages = repository.findAll();

        
        
        Double totalRevenue = packages.stream()
                .filter(pkg -> "SORTED".equals(pkg.getStatus()))
                .mapToDouble(pkg -> pkg.getWeight() * 2.50)
                .sum();

        return totalRevenue;
    }
    
   


}