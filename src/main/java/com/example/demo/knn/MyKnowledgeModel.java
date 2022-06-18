package com.example.demo.knn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.filters.unsupervised.instance.Resample;


public class MyKnowledgeModel {
    //Lưu đường dẫn nguồn dữ liệu
    DataSource source;
    //Lưu các trường trong tập dữ liệu
    Instances dataset;
    String[] data_option;
    String[] model_option;
    Instances trainset;
    Instances testset;

    public MyKnowledgeModel() {
    }

    //Hàm truyền vào đường dẫn và lấy dữ liệu từ tập dataset
    public MyKnowledgeModel(String filename, String m_opts,
                            String d_opts) throws Exception {

        if(!filename.isEmpty()){
            this.source = new DataSource(filename);
            this.dataset = source.getDataSet();
        }
        if (m_opts != null) {
            this.model_option = weka.core.Utils.splitOptions(m_opts);
        }
        if (d_opts != null) {
            this.data_option = weka.core.Utils.splitOptions(d_opts);
        }
    }

    //Lưu file vào một file khác ở vị trí khác
    public void saveData (String filename) throws Exception{
        //Khởi tạo đối tượng ArffSaver để tương tác với dữ liệu của file truyền vào
        ArffSaver outputData = new ArffSaver();
        //Set các trường dữ liệu từ tập dataset vào outputData
        outputData.setInstances(this.dataset);
        //Set File dữ liệu vào file mới
        outputData.setFile(new File(filename));
        //Ghi dữ liệu lên file mới
        outputData.writeBatch();
        System.out.println("Saved Finished!");
    }

    //Lưu file thành file CSV
    public void saveDataCsv (String filename) throws IOException{
        //Khởi tạo đối tượng CSVSaver để tương tác với dữ liệu của file truyền vào
        CSVSaver outDataCsv = new CSVSaver();
        outDataCsv.setInstances(this.dataset);
        outDataCsv.setFile(new File(filename));
        outDataCsv.writeBatch();
        System.out.println("Save CSV finished!");
    }

    //Apriori (Remove)
    public Instances removeData (Instances originalData) throws Exception{
        Remove remove = new Remove();
        remove.setOptions(data_option);
        remove.setInputFormat(originalData);
        return Filter.useFilter(originalData, remove);
    }

    /*Aptiori chỉ sử dụng ở kiểu Nominal nên phải sử sụng phương thức chuyển đổi
        từ Numeric sang Nominal để thực hiện */
    public Instances convertNumtoNom (Instances originalData) throws Exception{
        NumericToNominal convert = new NumericToNominal();
        convert.setOptions(this.data_option);
        convert.setInputFormat(originalData);
        return Filter.useFilter(originalData, convert);
    }

    //FPGrowth sử dụng Binary nên phải chuyển Nominal thành Binary
    public Instances convertNomToBnr (Instances originalData) throws Exception{
        NominalToBinary convert = new NominalToBinary();
        convert.setOptions(this.data_option);
        convert.setBinaryAttributesNominal(true);
        convert.setInputFormat(originalData);
        return Filter.useFilter(originalData, convert);
    }

    //Split dataset to trainset and testset with removepercentage
    public Instances splitDatasetRemovePer (Instances originalData,
                                            double percent, boolean isTest) throws Exception{
        RemovePercentage rp = new RemovePercentage();
        rp.setPercentage(percent);
        rp.setInvertSelection(isTest);
        rp.setInputFormat(originalData);
        return Filter.useFilter(originalData, rp);
    }

    //Split dataset with Resample
    public Instances splitDatasetResample (Instances originalData,
                                           double percent, boolean isTest) throws Exception{
        Resample rs = new Resample();
        rs.setNoReplacement(true);
        rs.setSampleSizePercent(percent);
        rs.setInvertSelection(isTest);
        rs.setInputFormat(originalData);
        return Filter.useFilter(originalData, rs);
    }

    //Lưu trữ mô hình
    public void saveModel(String filename, Object model)
            throws Exception{
        weka.core.SerializationHelper.write(filename, model);
    }

    //Load mô hình
    public Object loadModel(InputStream filename) throws Exception{
        return weka.core.SerializationHelper.read(filename);
    }

    //Lay du lieu tu tap trainset cho mo hinh phan lop
    public void setTrainset(InputStream input) throws Exception{
        DataSource trainSource = new DataSource(input);
        this.trainset = trainSource.getDataSet();
    }

    //Lay du lieu tu tap testset cho mo hinh phan lop
    public void setTestset(InputStream input) throws Exception{
        DataSource testSource = new DataSource(input);
        this.testset = testSource.getDataSet();
    }
    @Override
    public String toString() {
        return dataset.toSummaryString();
    }
}
