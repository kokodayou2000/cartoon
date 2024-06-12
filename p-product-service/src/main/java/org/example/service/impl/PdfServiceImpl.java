package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.example.service.IPdfService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PdfServiceImpl implements IPdfService {


//    @Autowired
//    IFileService fileService;
//
//    /**
//     * 获取到了 imageUrlList 根据这些 url 下载，然后转换成pdf
//     * @param imageUrls
//     * @return
//     * @throws IOException
//     */
//    @SneakyThrows
//    @Override
//
//    public String createPdfFromImage(List<String>imageUrls) {
//
//        List<BufferedImage> bufferedImages = getImageBytesFromUrl(imageUrls);
//
//        PDDocument doc = new PDDocument();
//        for (BufferedImage bufferedImage : bufferedImages) {
//            int imageHeight = bufferedImage.getHeight();
//            int imageWidth = bufferedImage.getWidth();
//            log.info("imageHeight {}",imageHeight);
//            log.info("imageWidth {}",imageWidth);
//            PDPage page = new PDPage(PDRectangle.A4);
//            doc.addPage(page);
//            PDImageXObject image = LosslessFactory.createFromImage(doc, bufferedImage);
//            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
//            contentStream.drawImage(image, 0, 0, imageWidth, imageHeight);
//            contentStream.close();
//        }
//
//        // 写入到临时文件
//        String filePath = "./" + CommonUtil.getRandomFileName();
//        File tempFile = new File(filePath);
//        // 新建FileO
//        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
//            doc.save(outputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            doc.close();
//        }
//        // 上传到 minio
//        return  fileService.uploadToMinio(new FileInputStream(filePath), "application/pdf", "test");
//    }
//
//    /**
//     * 根据url获取 buffered List
//     * @param imageUrls 图片连接
//     * @return buffered list
//     * @throws IOException
//     */
//    public static List<BufferedImage> getImageBytesFromUrl(List<String> imageUrls) throws IOException {
//
//        OkHttpClient client = new OkHttpClient();
//
//        List<BufferedImage> loadedImages = new ArrayList<>();
//
//
//        for (String imageUrl : imageUrls) {
//            Request request = new Request.Builder()
//                    .url(imageUrl)
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Unexpected code " + response);
//                    }
//
//                    ResponseBody responseBody = response.body();
//                    if (responseBody != null) {
//                        InputStream inputStream = responseBody.byteStream();
//                        BufferedImage image = ImageIO.read(inputStream);
//                        // 将加载的图片添加到数组中
////                        synchronized (loadedImages) {
//                        loadedImages.add(image);
////                        }
//                        inputStream.close();
//                    }
//                }
//            });
//        }
//        while (loadedImages.size() < imageUrls.size()) {
//            try {
//                Thread.sleep(10); // 等待一段时间再检查
//                System.out.println(imageUrls.size());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return loadedImages;
//    }
}
