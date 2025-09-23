package lab.docx;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.properties.table.tr.TrHeight;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 11:49 2024/10/14
 * @Modified By:
 */
public class WordTest {
    private static final ObjectFactory factory = Context.getWmlObjectFactory();

    public static void main(String[] args) throws Exception {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        // 页眉
        HeaderPart headerPart = createHeader(wordPackage);
        String imageFilePath = "/Users/chengyingzhang/Downloads/cdwy.png";
        headerPart.getContent().add(newImage(wordPackage, headerPart, imageFilePath));
        // 标题
        addTitle(wordPackage);
        // 说明：日期：根据系统时间默认         记录人：系统根据登录账号默认    项目名称：系统根据登录账号
        addDesc(wordPackage);

        // 表格
        addTable(wordPackage);

        // 换行符
        addBreakPage(wordPackage);

        // 标题
        addTitle(wordPackage);
        // 说明：日期：根据系统时间默认         记录人：系统根据登录账号默认    项目名称：系统根据登录账号
        addDesc(wordPackage);

        // 表格
        addTable(wordPackage);

        // addImage1();


        File exportFile = new File("welcome.docx");
        wordPackage.save(exportFile);

    }

    private static void addDesc(WordprocessingMLPackage wordPackage) {
//        日期：根据系统时间默认         记录人：系统根据登录账号默认    项目名称：系统根据登录账号
        P p = factory.createP();
        p.setPPr(pCenterPPr());
        addLabelAndText(p, "日期：", "2024-10-14" + "       ");
        addLabelAndText(p, "记录人：", "chengyingzhang" + "        ");
        addLabelAndText(p, "项目名称：", "CDWY");
        wordPackage.getMainDocumentPart().addObject(p);
    }

    private static void addLabelAndText(P p, String label, String text) {
        R labelR1 = getLabelR(label);
        p.getContent().add(labelR1);
        R textR1 = getTextR(text);
        p.getContent().add(textR1);
    }

    @NotNull
    private static R getTextR(String text) {
        R textR1 = factory.createR();
        textR1.setRPr(textRpr());
        Text text1 = factory.createText();
        text1.setValue(text);
//        保留空格
        text1.setSpace("preserve");
        textR1.getContent().add(text1);
        return textR1;
    }

    @NotNull
    private static R getLabelR(String label) {
        R labelR1 = factory.createR();
        labelR1.setRPr(labelRpr());
        Text labelText = factory.createText();
        labelText.setValue(label);
        labelR1.getContent().add(labelText);
        return labelR1;
    }

    private static RPr labelRpr() {
        RPr rPr = factory.createRPr();
        // font size
        HpsMeasure size = new HpsMeasure();
        size.setVal(BigInteger.valueOf(24));
        rPr.setSz(size);
        rPr.setSzCs(size);
        return rPr;
    }

    private static RPr textRpr() {
        RPr rPr = factory.createRPr();
        // font size
        HpsMeasure size = new HpsMeasure();
        size.setVal(BigInteger.valueOf(18));
        rPr.setSz(size);
        rPr.setSzCs(size);
        return rPr;
    }

    private static void addTitle(WordprocessingMLPackage wordPackage) {
        P p = factory.createP();
        R r = factory.createR();
        RPr rPr = factory.createRPr();
        Text text = factory.createText();
        text.setValue("CDWY工作记录");

        //设置字体
        RFonts font = new RFonts();
        font.setAscii("微软雅黑");
        font.setEastAsia("微软雅黑");//经测试发现这个设置生效
        rPr.setRFonts(font);
        //设置粗体
        BooleanDefaultTrue bold = factory.createBooleanDefaultTrue();
        bold.setVal(Boolean.TRUE);
        rPr.setB(bold);
        // font size
        HpsMeasure size = new HpsMeasure();
        size.setVal(BigInteger.valueOf(48));
        rPr.setSz(size);
        rPr.setSzCs(size);
        r.setRPr(rPr);

        PPr pPr = pCenterPPr();
        p.setPPr(pPr);

        r.getContent().add(text);
        p.getContent().add(r);
        wordPackage.getMainDocumentPart().addObject(p);
    }

    @NotNull
    private static PPr pCenterPPr() {
        // ppr
        PPr pPr = factory.createPPr();
        //creating the alignment
        Jc jc = new Jc();
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);
        return pPr;
    }
//
//    private static void addImage1() {
//
//        P p = createNormalText("Hello Baeldung");
//        mainDocumentPart.getContent().add(p);
//        // 图片
//        File image = new File("/Users/chengyingzhang/Downloads/image.png");
//        byte[] bytes = Files.readAllBytes(image.toPath());
//        BinaryPartAbstractImage binaryPartAbstractImage = BinaryPartAbstractImage.createImagePart(wordPackage, bytes);
//        Inline inline = binaryPartAbstractImage.createImageInline(
//                "Baeldung Image (filename hint)", "Alt Text", 1, 2, false);
//        P imageP = addImageToParagraph(inline);
//        mainDocumentPart.addObject(imageP);
//    }

    /**
     * 创建包含图片的内容
     *
     * @param word
     * @param sourcePart
     * @param imageFilePath
     * @return
     * @throws Exception
     */
    public static P newImage(WordprocessingMLPackage word,
                             Part sourcePart,
                             String imageFilePath) throws Exception {
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage
                .createImagePart(word, sourcePart, new File(imageFilePath));
        //随机数ID
        int id = (int) (Math.random() * 10000);
        //这里的id不重复即可
        Inline inline = imagePart.createImageInline("image", "image", id, id * 2, false);

        Drawing drawing = factory.createDrawing();
        drawing.getAnchorOrInline().add(inline);

        R r = factory.createR();
        r.getContent().add(drawing);

        P p = factory.createP();
        p.getContent().add(r);

        return p;
    }

    /**
     * 创建页眉
     *
     * @param word
     * @return
     * @throws Exception
     */
    public static HeaderPart createHeader(
            WordprocessingMLPackage word) throws Exception {
        HeaderPart headerPart = new HeaderPart();
        Relationship rel = word.getMainDocumentPart().addTargetPart(headerPart);
        createHeaderReference(word, rel);
        return headerPart;
    }

    /**
     * 创建页眉引用关系
     *
     * @param word
     * @param relationship
     */
    public static void createHeaderReference(
            WordprocessingMLPackage word,
            Relationship relationship) {
        List<SectionWrapper> sections = word.getDocumentModel().getSections();

        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        // There is always a section wrapper, but it might not contain a sectPr
        if (sectPr == null) {
            sectPr = factory.createSectPr();
            word.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size() - 1).setSectPr(sectPr);
        }
        HeaderReference headerReference = factory.createHeaderReference();
        headerReference.setId(relationship.getId());
        headerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(headerReference);
    }

    private static void addBreakPage(WordprocessingMLPackage wordPackage) {
        ObjectFactory wmlObjectFactory = Context.getWmlObjectFactory();
        P p = wmlObjectFactory.createP();
        R r = wmlObjectFactory.createR();
        Br pageBreak = new Br();
        pageBreak.setType(STBrType.PAGE);
        r.getContent().add(pageBreak);
        p.getContent().add(r);
        wordPackage.getMainDocumentPart().addObject(p);
    }

    private static void addTable(WordprocessingMLPackage wordPackage) {
        int writableWidthTwips = wordPackage.getDocumentModel()
                .getSections().get(0).getPageDimensions().getWritableWidthTwips();
        ObjectFactory wmlObjectFactory = Context.getWmlObjectFactory();
        // 创建表格
        Tbl table = wmlObjectFactory.createTbl();
        // 样式
        table.setTblPr(new TblPr());

        table.getTblPr().setTblBorders(getTblBorders());//设置表格线

        table.getTblPr().setTblW(getTblWidth(5000));// 设置表格宽度

        int rowIndex = 0;
        for (int i = 0; i < 5; i++) {
            createRow(table, "标题", "第" + (++rowIndex) + "行");
        }
        wordPackage.getMainDocumentPart().addObject(table);
    }

    //设置表格宽度

    private static TblWidth getTblWidth(int w) {

        TblWidth width = new TblWidth();

        width.setW(BigInteger.valueOf(w));

        width.setType("pct");

        //此处试了几种方式均不好用,只有这个pct的合适,50分之一是一个百分点,5000为百分之百的宽度

        return width;

    }


    //表格边框

    private static TblBorders getTblBorders() {

        //构造边框样式

        CTBorder border = new CTBorder();

        border.setColor("red");

        border.setSz(new BigInteger("4"));

        border.setSpace(new BigInteger("0"));

        border.setVal(STBorder.SINGLE);

        //设置边框的上下左右边框

        TblBorders borders = new TblBorders();

        borders.setTop(border);

        borders.setBottom(border);

        borders.setLeft(border);

        borders.setRight(border);

        return borders;

    }


    //单元格边框

    private static TcPrInner.TcBorders getTcBorders() {

        //构造边框样式

        CTBorder border = new CTBorder();

        border.setColor("black");

        border.setSz(new BigInteger("15"));

        border.setSpace(new BigInteger("0"));

        border.setVal(STBorder.SINGLE);

        //设置边框的上下左右边框

        TcPrInner.TcBorders borders = new TcPrInner.TcBorders();

        borders.setTop(border);

        borders.setBottom(border);

        borders.setLeft(border);

        borders.setRight(border);

        return borders;

    }

    private static final String songTi = "宋体";
    private static P createNormalText(String s) {
        ObjectFactory wmlObjectFactory = Context.getWmlObjectFactory();
        P p = wmlObjectFactory.createP();
        R r = wmlObjectFactory.createR();

        setFontSong(r);
        Text text = wmlObjectFactory.createText();
        text.setValue(s);
        r.getContent().add(text);
        p.getContent().add(r);
        return p;
    }

    private static void setFontSong(R r) {
        RFonts font = new RFonts();
        font.setAscii(songTi);
        font.setEastAsia(songTi);
        RPr rPr = factory.createRPr();
        rPr.setRFonts(font);
        r.setRPr(rPr);
    }


    private static void createRow(Tbl table, String... cells) {
        ObjectFactory wmlObjectFactory = Context.getWmlObjectFactory();
        Tr tableRow = wmlObjectFactory.createTr();
        setTrHeight(tableRow, 500);
        int columnIndex = 0;
        for (String cellText : cells) {
            Tc tableCell = wmlObjectFactory.createTc();
            // 样式
            tableCell.setTcPr(new TcPr());
            // 边框
            tableCell.getTcPr().setTcBorders(getTcBorders());
            // 宽度
            int percent = columnIndex == 0 ? 25 : 75;
            int w = (percent * 5000) / 100;
            tableCell.getTcPr().setTcW(getTblWidth(w));

            P normalText = createNormalText(cellText);
            tableCell.getContent().add(normalText);
            tableRow.getContent().add(tableCell);
            columnIndex++;
        }
        table.getContent().add(tableRow);
    }

    /**
     * 设置tr高度
     */
    public static void setTrHeight(Tr tr, Integer height) {
        TrPr trPr = tr.getTrPr();
        if (Objects.isNull(trPr)) {
            trPr = factory.createTrPr();
            tr.setTrPr(trPr);
        }
        CTHeight ctHeight = new CTHeight();
        ctHeight.setVal(BigInteger.valueOf(height));
        TrHeight trHeight = new TrHeight(ctHeight);
        trHeight.set(trPr);
    }

    private static P addImageToParagraph(Inline inline) {
        ObjectFactory factory = new ObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        p.getContent().add(r);
        Drawing drawing = factory.createDrawing();
        r.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return p;
    }
}
