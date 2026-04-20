# Projeto Integrado - Bootcamp 2026

---

## 🧩 Arquitetura

> Inserir aqui o diagrama de arquitetura (ver instruções na seção 2)

---

## 🔄 Fluxos de Integração

### 1. Commerce → AEM (Dia 15)

* Adobe Commerce expõe catálogo via API REST
* AEM consome via Sling Model (`HttpURLConnection`)
* Componente **Product Showcase** renderiza produtos

**Endpoint:**
`GET /V1/bootcamp/products`

---

### 2. Shopify → Hydrogen (Dia 16)

* Shopify gerencia catálogo via Admin
* Hydrogen consome via **Storefront API (GraphQL)**
* Storefront headless com React/Remix

---

### 3. AEM → Hydrogen (Dia 17)

* AEM gerencia Content Fragments
* Hydrogen consome via GraphQL (ex: página `/about`)

**Endpoint:**
`POST /content/cq:graphql/global/endpoint.json`

---

### 4. AEM → Commerce (Dia 17)

* AEM gerencia Experience Fragments (banners/promos)
* Commerce consome via JSON Export

**Endpoint:**
`GET /content/experience-fragments/bootcamp-wknd/us/en/banner-promo-bootcamp/master.model.json`

---

### 5. Dashboard de Integração (Dia 18)

* Hydrogen agrega dados das três plataformas
* Consumo paralelo de APIs com `Promise.allSettled`

**Rota:**
`/dashboard`

---

## 📊 Tabela de Endpoints

| Plataforma | Tipo    | URL                     | Auth       |
| ---------- | ------- | ----------------------- | ---------- |
| Commerce   | REST    | /V1/bootcamp/products   | Anonymous  |
| AEM        | GraphQL | /content/cq:graphql/global/endpoint.json | Basic Auth |
| AEM        | JSON    | /content/experience-fragments/bootcamp-wknd/us/en/banner-promo-bootcamp/master.model.json   | Basic Auth |
| Shopify    | GraphQL | Storefront API          | Token      |

---
