<?php

namespace Bootcamp\CatalogApi\Model;

use Bootcamp\CatalogApi\Api\ProductListInterface;
use Magento\Catalog\Api\ProductRepositoryInterface;
use Magento\Catalog\Model\Product\Visibility;
use Magento\Framework\Api\SearchCriteriaBuilder;

class ProductList implements ProductListInterface
{
    private ProductRepositoryInterface $productRepository;
    private SearchCriteriaBuilder $searchCriteriaBuilder;
    private Visibility $visibility;

    public function __construct(
        ProductRepositoryInterface $productRepository,
        SearchCriteriaBuilder $searchCriteriaBuilder,
        Visibility $visibility
    ) {
        $this->productRepository = $productRepository;
        $this->searchCriteriaBuilder = $searchCriteriaBuilder;
        $this->visibility = $visibility;
    }

    /**
     * @return array
     */
    public function getList()
    {
        $searchCriteria = $this->searchCriteriaBuilder
            ->addFilter('sku', 'BOOT-%', 'like')
            ->addFilter('visibility', $this->visibility->getVisibleInSiteIds(), 'in')
            ->create();
        $products = $this->productRepository->getList($searchCriteria);
        $result = [];
        foreach ($products->getItems() as $product) {
            $result[] = [
                'sku' => $product->getSku(),
                'name' => $product->getName(),
                'price' => $product->getPrice(),
                'type' => $product->getTypeId(),
                'description' => $product->getCustomAttribute('short_description')
                    ? $product->getCustomAttribute('short_description')->getValue()
                    : '',
                'bootcamp_highlight' => $product->getCustomAttribute('bootcamp_highlight')
                    ? (bool) $product->getCustomAttribute('bootcamp_highlight')->getValue()
                    : false,
                'tech_stack' => $product->getCustomAttribute('tech_stack')
                    ? $product->getCustomAttribute('tech_stack')->getValue()
                    : '',
                'image_url' => $product->getCustomAttribute('image')
                    ? $product->getCustomAttribute('image')->getValue()
                    : '',
            ];
        }
        return $result;
    }
}
