package com.example.demo.service;

import com.example.demo.dto.PatientDTO;
import com.example.demo.dto.ResultDto;
import com.example.demo.knn.MyKNNModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import weka.classifiers.lazy.IBk;

import java.io.InputStream;
import java.io.OutputStream;

@Service
public class PatientService {
    public ResultDto predict(PatientDTO patientDTO) throws Exception {

//        Resource resource_train = new ClassPathResource("Dataset_of_Diabetes_Cleaned_train.arff");
//        Resource resource_test = new ClassPathResource("Dataset_of_Diabetes_Cleaned_tesr.arff");
        Resource resource_model = new ClassPathResource("knnmodel.model");

//        InputStream inputStream_train = resource_train.getInputStream();
//        InputStream inputStream_test = resource_test.getInputStream();
        InputStream inputStream_model = resource_model.getInputStream();


//        MyKNNModel model = new MyKNNModel(
//                "",
//                "-K 5 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\"",
//                null);
//
//        model.buildKNN(inputStream_train);
//        model.evaluateKNN(inputStream_test);
//        model.saveModel("D:\\knnmodel.model", model.knn);
        MyKNNModel model = new MyKNNModel();
        model.knn = (IBk) model.loadModel(inputStream_model);
        return model.predictClassLabel(patientDTO);
    }
}
