cd .\p-coupon-service\
mvn install dockerfile:build
cd ..
cd .\p-gateway\
mvn install dockerfile:build
cd ..
cd .\p-order-service\
mvn install dockerfile:build
cd ..
cd .\p-product-service\
mvn install dockerfile:build
cd ..
cd .\p-user-service\
mvn install dockerfile:build
cd ..

