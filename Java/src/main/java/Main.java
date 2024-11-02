import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {

        S3Client s3Client = new S3Provider().getS3Client();
        String bucketName = "edutech-s3";

        try {
            List<S3Object> objects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build()).contents();
            for (S3Object object : objects) {
                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(object.key())
                        .build();

                InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
                Files.copy(inputStream, new File(object.key()).toPath());
                System.out.println("Arquivo baixado: " + object.key());
            }
        } catch (IOException | S3Exception e) {
            System.err.println("Erro ao fazer download dos arquivos: " + e.getMessage());
        }

        String filenameEM = "SAEB_3EM_FILTRADO_50K.xlsx";
        Path pathEM = Path.of(filenameEM);
        InputStream fileEM = Files.newInputStream(pathEM);

        String filenameQ = "TS_ITEM.xlsx";
        Path pathQ = Path.of(filenameQ);
        InputStream fileQ = Files.newInputStream(pathQ);

        ApachePOI apachePOI = new ApachePOI();
        apachePOI.extrairDadosEM(filenameEM,fileEM, apachePOI.extrairQuestoes(filenameQ,fileQ));

    }
}
