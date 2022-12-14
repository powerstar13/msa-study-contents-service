# ๐ ์นํฐ ์๋น์ค ํ๋ซํผ

---

# ๐ง ํ๊ฒฝ ๊ตฌ์ฑ

1. JDK 11
2. SpringBoot 2.7.2
3. Gradle

# โน๏ธ ์คํ ๋ฐฉ๋ฒ

## ๐ Terminal์ ์ฌ์ฉํ  ๊ฒฝ์ฐ
- (JDK ์ค์น ํ JAVA_HOME ํ๊ฒฝ ๋ณ์ ์ค์ ํ๋ค๊ณ  ๊ฐ์ )

`out ํด๋ ๊ฒฝ๋ก์ ๊ฐ jar ์คํ ๋ช๋ น์ ์๋ ฅ ๋ฐ๋๋๋ค.`

1. API Gateway ์คํ
    ```shell
    # ์ฌ๊ธฐ์ `./`์ README ๊ฒฝ๋ก ์์น๋ฅผ ๊ธฐ์ค์ผ๋ก ์์ฑํจ
    java -jar ./out/webtoon-gateway.jar
    ```
2. ํ์ ์๋น์ค ์คํ
    ```shell
    java -jar ./out/webtoon-member.jar
    ```
3. ์ํ ์๋น์ค ์คํ
    ```shell
    java -jar ./out/webtoon-contents.jar
    ```
4. ์ด๋ ฅ ์๋น์ค ์คํ
    ```shell
    java -jar ./out/webtoon-history.jar
    ```

## โค๏ธ IntelliJ๋ฅผ ์ฌ์ฉํ  ๊ฒฝ์ฐ
- (Project Structure์ SDK์ Import Module์ ์ค์ ํ๋ค๊ณ  ๊ฐ์ )
- (Settings์ Gradle JVM์ SDK๋ฅผ ์ค์ ํ๋ค๊ณ  ๊ฐ์ )

1. `./out/` ํด๋ ๋ด์ jar ํ์ผ๋ค์ ์ฐํด๋ฆญ ํ Runํ์ฌ ๋ชจ๋ ์คํ
   1. API Gateway port: 8080
   2. ํ์ ์๋น์ค port: 8081
   3. ์ํ ์๋น์ค port: 8082
   4. ์ด๋ ฅ ์๋น์ค port: 8083

## ๐ฐ [API ๋ฌธ์](http://localhost:8080/docs/index.html)

`jar ํ์ผ๋ค์ ๋ชจ๋ ์คํ์ํจ ํ API ๋ฌธ์๋ฅผ ์ด์ด์ฃผ์๊ธฐ ๋ฐ๋๋๋ค.`

## ๐ [API Test](./http-test/api.http)

`API๋ฅผ ์คํํ  ์ ์๋ http test ํ์ผ์๋๋ค.`

---

# ๐ก ๋ง์ดํฌ๋ก์๋น์ค ๋ชจ๋ธ๋ง

![๋ง์ดํฌ๋ก์๋น์ค ๋ชจ๋ธ๋ง](./public/webtoon-domain-model.png)

# ๐  ERD ์ค๊ณ

![ERD ์ค๊ณ](./public/webtoon-erd.png)

# ๐ญ ์์คํ ์ํคํ์ฒ

![์์คํ ์ํคํ์ฒ](./public/webtoon-system-architecture.png)

---
