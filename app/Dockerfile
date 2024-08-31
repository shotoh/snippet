FROM node:20.17.0

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install
COPY . ./

CMD ["npm", "start"]