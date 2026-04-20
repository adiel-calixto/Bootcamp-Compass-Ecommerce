# Bootcamp WKND - AEM Project

Projeto Adobe Experience Manager (AEM) Maven multi-module.

## Customização

### Componente ProductShowcase (`ui.apps/src/main/content/jcr_root/apps/bootcamp-wknd/components/product-showcase`)

Componente que consome produtos do Magento via API REST e exibe no AEM:

- **Sling Model:** `core/src/main/java/com/bootcamp/core/models/ProductShowcaseModel.java`
- **Template HTL:** `product-showcase.html`
- Consome endpoint: `GET /V1/bootcamp/products`

## Comandos Úteis

```bash
# Build completo
mvn clean install

# Build + deploy para author (porta 4502)
mvn clean install -PautoInstallSinglePackage

# Build + deploy para publish (porta 4503)
mvn clean install -PautoInstallSinglePackagePublish

# Build frontend (ui.frontend)
cd ui.frontend && npm run prod
```

## Estrutura

```
bootcamp-wknd/
├── core/           # OSGi bundle (Java)
├── ui.apps/        # Componentes, templates, clientlibs
├── ui.frontend/    # Build frontend (Webpack, TypeScript, SCSS)
├── ui.content/     # Conteúdo sample
├── all/            # Pacote completo (todos os módulos)
└── dispatcher/     # Configuração Apache Dispatcher
