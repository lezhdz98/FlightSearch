import React from "react";
import { Descriptions } from "antd";
import type { DescriptionsProps } from "antd";
import { PriceBreakdown } from "../../interfaces/PriceBreakdown";
import { TravelerPricing } from "../../interfaces/TravelerPricing";

interface PriceInfoProps {
  price: PriceBreakdown;
  traveler: TravelerPricing;
}

const PriceInfo: React.FC<PriceInfoProps> = ({ price, traveler }) => {
  const items: DescriptionsProps["items"] = [
    {
      key: "1",
      label: "Total Price",
      children: `$ ${price.totalPrice}`,
    },

    {
      key: "2",
      label: "Price per Traveler",
      children: `$ ${traveler.totalPrice}`,
    },
  ];
  return (
    <Descriptions
      title="Price"
      items={items}
      column={1}
      style={{
        maxWidth: 250,
        textAlign: "left",
        padding: "25px",
      }}
    ></Descriptions>
  );
};

export default PriceInfo;
