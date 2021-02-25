FROM node:12.18.3 as builder

# 작업 폴더 만들고 npm 설치
RUN mkdir /usr/src/app
WORKDIR /usr/src/app
ENV PATH /usr/src/app/node_modules/.bin:$PATH
COPY package.json /usr/src/app/package.json
RUN npm install --silent
RUN npm install react-scripts@3.4.3 -g --silent

# 소스를 작업폴더로 복사하고 빌드
COPY . /usr/src/app
RUN npm run build

FROM nginx:latest
# nginx 기본설정 삭제 및 앱에서 설정한 파일 복사
RUN rm -rf /etc/nginx/conf.d
COPY conf /etc/nginx

COPY --from=builder /usr/src/app/build /usr/share/nginx/html

# 80포트 오픈 및 nginx 실행
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]