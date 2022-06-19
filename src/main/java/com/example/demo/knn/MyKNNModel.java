package com.example.demo.knn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import com.example.demo.dto.PatientDTO;
import com.example.demo.dto.ResultDto;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.Debug;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author CUONG
 */
public class MyKNNModel extends MyKnowledgeModel{
    public IBk knn;
    Evaluation eval;

    public MyKNNModel() {
    }

    public MyKNNModel(String filename, String m_opts, String d_opts) throws Exception {
        super(filename, m_opts, d_opts);
    }

    public void buildKNN (InputStream input) throws Exception{
        setTrainset(input);
        this.trainset.setClassIndex(this.trainset.numAttributes() - 1);
        this.knn = new IBk();
        knn.setOptions(this.model_option);
        knn.buildClassifier(trainset);
    }

    public void evaluateKNN (InputStream input) throws Exception{
        //Doc test vao bo nho
        setTestset(input);
        this.testset.setClassIndex(this.testset.numAttributes() - 1);
        //Danh gia mo hinh bang 8-folds crossvalidate
        Random rnd = new Debug.Random(1);
        int folds = 10;
        eval = new Evaluation(this.trainset);
        eval.crossValidateModel(knn, this.testset, folds, rnd);
        System.out.println(eval.toSummaryString("\nKet qua\n------------\n"
                , false));
    }

    public ResultDto predictClassLabel(PatientDTO patientDTO) throws Exception{
        //Doc du lieu can du doan vao bo nho
        double gt = 0;
        if(patientDTO.getGender() == "M"){
            gt = 1;
        }

        ArrayList<Attribute> attributeList = new ArrayList<Attribute>();

        Attribute gender = new Attribute("Gender");
        Attribute age = new Attribute("AGE");
        Attribute urea = new Attribute("Urea");
        Attribute cr  = new Attribute("Cr");
        Attribute hbA1c  = new Attribute("HbA1c");
        Attribute chol  = new Attribute("Chol");
        Attribute tg  = new Attribute("TG");
        Attribute hdl  = new Attribute("HDL");
        Attribute ldl  = new Attribute("LDL");
        Attribute vldl  = new Attribute("VLDL");
        Attribute bmi  = new Attribute("BMI");


        ArrayList<String> classVal = new ArrayList<String>();
        classVal.add("N");
        classVal.add("P");
        classVal.add("Y");
        Attribute label = new Attribute("@@class@@",classVal);

        attributeList.add(gender);
        attributeList.add(age);
        attributeList.add(urea);
        attributeList.add(cr);
        attributeList.add(hbA1c);
        attributeList.add(chol);
        attributeList.add(tg);
        attributeList.add(hdl);
        attributeList.add(ldl);
        attributeList.add(vldl);
        attributeList.add(bmi);
        attributeList.add(label);

        Instances data = new Instances("TestInstances",attributeList,0);

        // Create instances for each pollutant with attribute values latitude,
        // longitude and pollutant itself
        Instance inst_co = new DenseInstance(data.numAttributes());

        // Set instance's values for the attributes "latitude", "longitude", and
        // "pollutant concentration"
        inst_co.setValue(gender, gt);
        inst_co.setValue(age, patientDTO.getAge());
        inst_co.setValue(urea, patientDTO.getUrea());
        inst_co.setValue(cr, patientDTO.getCr());
        inst_co.setValue(hbA1c, patientDTO.getHbA1c());
        inst_co.setValue(chol, patientDTO.getChol());
        inst_co.setValue(tg, patientDTO.getTg());
        inst_co.setValue(hdl, patientDTO.getHdl());
        inst_co.setValue(ldl, patientDTO.getLdl());
        inst_co.setValue(vldl, patientDTO.getVldl());
        inst_co.setValue(bmi, patientDTO.getBmi());


        data.add(inst_co);

        //1,126,60,0,0,30.1,0.349,47,tested_positive
        //1,54,5,67,6.9,3.8,1.7,1.1,3,0.7,33,Y
        //0,50,4.7,46,4.9,4.2,0.9,2.4,1.4,0.5,24,N


        //1,59,5.2,38,5.4,4.9,1.2,0.9,3.4,0.6,23,N
        //1,55,4.8,60,6,3.6,3,1.5,0.8,1.4,24,P
        //1,57,9.3,69,8,6.1,3,1.1,2,1.4,28,Y

        data.setClassIndex(data.numAttributes() - 1);

        double predict = knn.classifyInstance(data.firstInstance());
        data.firstInstance().setClassValue(predict);

        System.out.println("\n" + predict);
        String result = "";
        if(predict == 0.0){
            result = "N";
        }else if (predict == 1.0){
            result = "P";
        }else if (predict == 2.0){
            result = "Y";
        }

        return new ResultDto(result);
    }
}

