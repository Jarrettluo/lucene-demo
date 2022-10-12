package com.jiaruiblog.lucenedemo.lucene;

import com.jiaruiblog.lucenedemo.util.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;


/**
 * @Author Jarrett Luo
 * @Date 2022/10/12 17:08
 * @Version 1.0
 */
class IndexTest {


    //创建索引
    @Test
    public void testCreateIndex() throws Exception {

        //指定索引库存放的路径
        //D:\temp\0108\index
        Directory directory = FSDirectory.open(new File("./lucene_index").toPath());
        //索引库还可以存放到内存中
        //Directory directory = new RAMDirectory();
        //创建一个标准分析器
        Analyzer analyzer = new StandardAnalyzer();
        //创建indexWriterConfig对象
        //第一个参数： Lucene的版本信息，可以选择对应的lucene版本也可以使用LATEST
        //第二根参数：分析器对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //创建indexWriter对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //原始文档的路径D:\传智播客\01.课程\04.lucene\01.参考资料\searchSource
        File dir = new File("./search_source");
        for (File f : Objects.requireNonNull(dir.listFiles())) {
            //文件名
            String fileName = f.getName();
            //文件内容
            String fileContent = FileUtils.readFileToString(f);
            //文件路径
            String filePath = f.getPath();
            //文件的大小
            long fileSize  = FileUtils.sizeOf(f);
            //创建文件名域
            //第一个参数：域的名称
            //第二个参数：域的内容
            //第三个参数：是否存储
            Field fileNameField = new TextField("filename", fileName, Field.Store.YES);
            //文件内容域
            Field fileContentField = new TextField("content", fileContent, Field.Store.YES);
            //文件路径域（不分析、不索引、只存储）
            Field filePathField = new StoredField("path", filePath);
            //文件大小域
            Field fileSizeField = new LongField("size", fileSize, Field.Store.YES);

            //创建document对象
            Document document = new Document();
            document.add(fileNameField);
            document.add(fileContentField);
            document.add(filePathField);
            document.add(fileSizeField);
            //创建索引，并写入索引库
            indexWriter.addDocument(document);
        }
        //关闭indexWriter
        indexWriter.close();
    }


}
