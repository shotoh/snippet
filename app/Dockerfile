FROM node:20.17.0 AS base
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . ./

FROM base AS dev
CMD ["npm", "start"]

FROM base AS build
RUN npm run build

FROM nginx:1.27.1 AS prod
COPY --from=build /app/build /usr/share/nginx/html
COPY --from=build /app/nginx/nginx.conf /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]