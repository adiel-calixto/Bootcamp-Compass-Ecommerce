# Bootcamp Commerce

Magento 2.4.8-p4 Community Edition com customizações.

## Módulos Customizados

### Bootcamp_CatalogApi (`app/code/Bootcamp/CatalogApi`)
Expõe endpoint REST para listar produtos do catálogo:

**Endpoint:** `GET /V1/bootcamp/products`

- `Api/ProductListInterface.php` - Contrato da API
- `Model/ProductList.php` - Implementação do repositório
- `etc/webapi.xml` - Rotas REST
- `etc/di.xml` - Configuração de injeção de dependência

### Bootcamp_AemContent (`app/code/Bootcamp/AemContent`)
Consome Experience Fragments do AEM para exibição de banners:

- `Block/AemBanner.php` - Bloco para renderizar banner
- `view/frontend/templates/aem-banner.phtml` - Template PHTML
- `etc/adminhtml/system.xml` - Configuração no admin

## Comandos Úteis

```bash
# Iniciar ambiente Docker
docker-compose up -d

# Limpar cache após alterações
php bin/magento cache:clean

# Compilar DI (se necessário)
php bin/magento setup:di:compile

# Habilitar módulos
php bin/magento module:enable Bootcamp_CatalogApi Bootcamp_AemContent

# Verificar status dos módulos
php bin/magento module:status
```
