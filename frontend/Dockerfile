FROM nginx:1.27-alpine
# Copia todo el frontend estático
COPY . /usr/share/nginx/html/
# Sobrescribe la config por defecto de nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80