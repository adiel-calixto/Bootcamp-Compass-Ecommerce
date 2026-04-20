<?php

namespace Bootcamp\AemContent\Block;

use Magento\Framework\HTTP\Client\Curl;
use Magento\Framework\View\Element\Template;
use Psr\Log\LoggerInterface;

class AemBanner extends Template
{
    private Curl $curl;
    private LoggerInterface $logger;

    public function __construct(
        Template\Context $context,
        Curl $curl,
        LoggerInterface $logger,
        array $data = []
    ) {
        parent::__construct($context, $data);
        $this->curl = $curl;
        $this->logger = $logger;
    }

    public function getAemContent(): array
    {
        $defaultUrl = 'http://host.docker.internal:4502/content/experience-fragments/bootcamp-wknd/us/en/banner-promo-bootcamp/master.model.json';
        $aemUrl = $this->getData('aem_url')
            ?: $defaultUrl;

        try {
            $this->curl->setCredentials('admin', 'admin');
            $this->curl->setTimeout(10);
            $this->curl->get($aemUrl);
            $response = $this->curl->getBody();
            $data = json_decode($response, true);
            return [
                'title' => $data[':items']['root'][':items']['title']['text'] ?? 'Bootcamp 2026',
                'text' => $data[':items']['root'][':items']['text']['text'] ?? '',
                'success' => true,
            ];
        } catch (\Exception $e) {
            $this->logger->error('AemContent: ' . $e->getMessage());
            return [
                'title' => 'Bootcamp 2026',
                'text' => 'Conteúdo indisponível no momento.',
                'success' => false,
                'error' => $e->getMessage(),
            ];
        }
    }
}
