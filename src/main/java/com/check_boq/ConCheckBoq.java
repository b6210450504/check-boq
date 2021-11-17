package com.check_boq;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConCheckBoq {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button accButton ,declineButton, excelButton ;

    // boq data
    @FXML
    TableView boqTable, matTable ;
    @FXML
    TextField searchBOQ ;
    @FXML
    Label idLabel, groupIDLabel, nameLabel, periodLabel,errLabel ;
    @FXML
    TextArea boqMemTextArea ;
    SerBoqDataList serBoqDataList ;
    ObservableList<MoBOQ> moBOQObservableList ;
    ObservableList<MoBOQ> masterData ;
    ObservableList<MoMatForBoq> masterDataMat ;
    MoBOQ selectedBoq ;

    // tor data
    @FXML
    TextArea torTextArea ;
    SerTorDataList serTorDataList ;

    // customer data
    SerCusDataList serCusDataList ;

    // materials data
    @FXML
    TextField searchMat ;
    SerMatDataList serMatDataList ;
    ArrayList<MoMatForBoq> moMatForBoqs ;
    @FXML
    Label totalLabel ;
    int total = 0 ;



    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                excelButton.setDisable(true);
                serBoqDataList = new SerBoqDataList() ;
                serTorDataList = new SerTorDataList() ;
                serCusDataList = new SerCusDataList() ;
                serMatDataList = new SerMatDataList() ;
                showBoqTable();
                masterData = FXCollections.observableList(serBoqDataList.getBoqArrayList()) ;
                errLabel.setMaxWidth(Double.MAX_VALUE);
                errLabel.setAlignment(Pos.CENTER);
                // BOQ Search Filter
                FilteredList<MoBOQ> filteredData = new FilteredList<>(masterData, p -> true);
                searchBOQ.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(boq -> {
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        if (boq.getBO_ProjName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        if(String.valueOf(boq.getBO_ID()).contains(lowerCaseFilter)){
                            return true;
                        }
                        if(String.valueOf(boq.getBO_GroupID()).contains(lowerCaseFilter)){
                            return true ;
                        }
                        return false;
                    });
                });
                SortedList<MoBOQ> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(boqTable.comparatorProperty());
                boqTable.setItems(sortedData);
                // listener Boq Table
                boqTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectBoq((MoBOQ) newValue);
                    }
                });
            }
        });
    }

    public void showBoqTable(){
        boqTable.getColumns().clear();
        moBOQObservableList = FXCollections.observableList(serBoqDataList.getBoqArrayList()) ;
        TableColumn nameCol = new TableColumn("Project Name");
        TableColumn idCol = new TableColumn("BOQ_ID");
        TableColumn groupID = new TableColumn("Group_ID") ;
        nameCol.setCellValueFactory(new PropertyValueFactory<MoBOQ,String>("BO_ProjName"));
        idCol.setCellValueFactory(new PropertyValueFactory<MoBOQ,Integer>("BO_ID"));
        groupID.setCellValueFactory(new PropertyValueFactory<MoBOQ,Integer>("BO_GroupID"));
        boqTable.getColumns().addAll(idCol,nameCol,groupID) ;
        boqTable.setItems(moBOQObservableList);
    }

    public void showSelectBoq(MoBOQ select){
        accButton.setDisable(false);
        declineButton.setDisable(false);
        excelButton.setDisable(false);
        selectedBoq = select ;
        showTorDes(select) ;
        idLabel.setText(String.valueOf(select.getBO_ID()));
        groupIDLabel.setText(serCusDataList.searchCusByID(select.getBO_GroupID()).getCS_Email())  ;
        nameLabel.setText(select.getBO_ProjName());
        periodLabel.setText(String.valueOf(select.getBO_Period()));
        showMemberBoq(select.getBO_Member()) ;
        showMatBoq(select.getBO_Materials()) ;
    }

    // Participants TextArea print
    public void showMemberBoq(String member){
        boqMemTextArea.clear();
        boqMemTextArea.appendText("Participant BOQ List\n");
        List<String> list = new ArrayList<String>(Arrays.asList(member.split(",")));
        int i = 1 ;
        for (String s:list) {
            boqMemTextArea.appendText( i +") "+ s + "\n");
            i++ ;
        }
    }

    // Tor TextArea print
    public void showTorDes(MoBOQ boq){
        torTextArea.clear();
        MoTOR tor = serTorDataList.getTorByProjName(boq.getBO_ProjName()); ;
        torTextArea.appendText("Project Name: " + tor.getTO_Name() + "\n\n");
        torTextArea.appendText("GroupID: " + tor.getTO_GroupID() +"\n\n");
        torTextArea.appendText("Participant List\n");
        List<String> list = new ArrayList<String>(Arrays.asList(tor.getTO_Member().split(",")));
        int i = 1 ;
        for (String s:list) {
            torTextArea.appendText( i +") "+ s + "\n");
            i++ ;
        }
        torTextArea.appendText("\nMaterial List\n");
        list = new ArrayList<String>(Arrays.asList(tor.getTO_Materials().split(",")));
        i = 1 ;
        for (String s:list) {
            torTextArea.appendText( i +") "+ s + "Qty." + "\n");
            i++ ;
        }
        torTextArea.appendText("\n\nPeriod: "+ tor.getTO_Period() + " day.");
    }

    public void showMatBoq(String mat){
        ArrayList<MoMatForBoq> matForShow = new ArrayList<>() ;

        List<String> list = new ArrayList<String>(Arrays.asList(mat.split(",")));
        // "{name=qty, name=qty}"
        List<String> list2 ;
//        ArrayList<String> matName = new ArrayList<>() ;
//        ArrayList<String> matQty = new ArrayList<>() ;
        for(int i = 0 ; i < list.size(); i++){
            list2 = new ArrayList<String>(Arrays.asList(list.get(i).split("="))) ;
//            matName.add(list2.get(0)) ;
//            matQty.add(list2.get(1)) ;
            MoMaterial temp = serMatDataList.getMatInfoByName(list2.get(0)) ;
            // (int mat_ID, String mat_Name, int mat_Price, int mat_Qty)
            matForShow.add(new MoMatForBoq(temp.getMat_ID(),temp.getMat_Name(),temp.getMat_Price(),Integer.valueOf(list2.get(1)))) ;
        }
        for (MoMatForBoq t: matForShow) {
            total += t.getMat_Total() ;
        }
        ObservableList<MoMatForBoq> moMatForBoqObservableList ;
        matTable.getColumns().clear();
        moMatForBoqObservableList = FXCollections.observableList(matForShow) ;
        moMatForBoqs = matForShow ;
        TableColumn nameCol = new TableColumn("Material Name");
        TableColumn priceCol = new TableColumn("Price");
        TableColumn idCol = new TableColumn("ID");
        TableColumn qtyCol = new TableColumn("Qty.");
        TableColumn totalCol = new TableColumn("Total");
        nameCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq,String>("Mat_Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq,Integer>("Mat_Price"));
        idCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq,Integer>("Mat_ID"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq,Integer>("Mat_Qty"));
        totalCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq, Long>("Mat_Total"));
        matTable.getColumns().addAll(idCol,nameCol,priceCol,qtyCol,totalCol) ;
        matTable.setItems(moMatForBoqObservableList);
        totalLabel.setText(String.valueOf(total));
    }

    public void eventAccept() throws IOException, MessagingException {
        String[] columns = {"ลำดับ","รายละเอียด", "ปริมาณ","ราคาต่อหน่วย", "รวม"};
        // exel object new
        Workbook workbook = new XSSFWorkbook();
        String fileName = selectedBoq.getBO_ProjName() ;
        Sheet sheet = workbook.createSheet("fileName");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 18);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        // sheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2)) ;
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,2)) ;
        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue(
                createHelper.createRichTextString("ชื่อโครงการ: " + selectedBoq.getBO_ProjName() +
                        "   GroupID: " + selectedBoq.getBO_GroupID())+ "    ขอบเขตการทำงาน: " + String.valueOf(selectedBoq.getBO_Period()) + " วัน");
        //create header row
        String[] memItem = selectedBoq.getBO_Member().split(",");
        StringBuilder mem = new StringBuilder();
        int ij = 1 ;
        for(String s: memItem){
            mem.append(ij).append(")").append(s).append(" ");
            ij++ ;
        }
        Row headerRow = sheet.createRow(2);
        row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue(createHelper.createRichTextString("ผู้มีส่วนเกี่ยวข้อง มีดังนี้ " + mem));
        // add Column
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 3 ;
        int k = 1 ;
        for(MoMatForBoq mat:moMatForBoqs){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(String.valueOf(k));
            k++ ;
            row.createCell(1).setCellValue(mat.getMat_Name());
            row.createCell(2).setCellValue(String.valueOf(mat.getMat_Qty()));
            row.createCell(3).setCellValue(String.valueOf(mat.getMat_Price()));
            row.createCell(4).setCellValue(String.valueOf(mat.getMat_Total()));
        }
        Row row2 = sheet.createRow(rowNum+2);
        row2.createCell(3).setCellValue(createHelper.createRichTextString("รวม")) ;
        row2.createCell(4).setCellValue(createHelper.createRichTextString(String.valueOf(total))) ;
        row2.createCell(5).setCellValue(createHelper.createRichTextString("บาท")) ;
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOut = new FileOutputStream("Excel/"+ fileName+".xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        sendEmail();
        excelButton.setDisable(false);
    }

    public void sendEmail() throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");
        // Create a mail session so you can create and send an email message.
        Session sessions = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("checking.boq@gmail.com", "Code1234");
            }
        });
        Message message = new MimeMessage(sessions);
        message.setSubject("นำส่งใบประเมินราคา(BOQ)ของโปรเจค " + selectedBoq.getBO_ProjName() + " ว่าจ้างโดย " + serCusDataList.searchCusByID(selectedBoq.getBO_GroupID()).getCS_Name());

        Address addressTo = new InternetAddress(serCusDataList.searchCusByID(selectedBoq.getBO_GroupID()).getCS_Email());

        message.setRecipient(Message.RecipientType.TO, addressTo);

        MimeMultipart multipart = new MimeMultipart();

        MimeBodyPart attachment = new MimeBodyPart();

        File file = new File("Excel/"+selectedBoq.getBO_ProjName()+".xlsx") ;
        accButton.setDisable(true);
        declineButton.setDisable(true);
        if(file.exists()){
            attachment.attachFile(file) ;
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Thank you for using the service.</h1>", "text/html");
            multipart.addBodyPart(messageBodyPart);
            MimeBodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("ลิงค์ข้อสอบถาม: "+"https://forms.gle/eP48xni8tZMyMzf57");

            multipart.addBodyPart(attachment);
            multipart.addBodyPart(messageBodyPart1);
            message.setContent(multipart);

            // send email
            Transport.send(message);
        }
    }

    public void eventDecline() throws IOException {
        if(!boqTable.getSelectionModel().isEmpty()){
            errLabel.setText("BOQ has been delete.");
            excelButton.setDisable(true);
            declineButton.setDisable(true);
            accButton.setDisable(true);
            torTextArea.clear();
            totalLabel.setText(null);
            nameLabel.setText(null);
            periodLabel.setText(null);
            groupIDLabel.setText(null);
            idLabel.setText(null);
            boqMemTextArea.clear();
            matTable.getColumns().clear();
            boqTable.getSelectionModel().clearSelection();
            serBoqDataList.delBoqOnDatabase(selectedBoq.getBO_ID());
            showBoqTable();
        }

    }

    public void eventOpenExcel() throws IOException {
        File file = new File("Excel/" + selectedBoq.getBO_ProjName() + ".xlsx") ;
        if(file.exists()){
            Desktop.getDesktop().open(new File("Excel/" + selectedBoq.getBO_ProjName() + ".xlsx"));
            excelButton.setDisable(true);
            declineButton.setDisable(true);
            accButton.setDisable(true);
            torTextArea.clear();
            totalLabel.setText(null);
            nameLabel.setText(null);
            periodLabel.setText(null);
            groupIDLabel.setText(null);
            idLabel.setText(null);
            boqMemTextArea.clear();
            matTable.getColumns().clear();
            boqTable.getSelectionModel().clearSelection();
            showBoqTable();
        }
    }


    public void eventBack( ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
