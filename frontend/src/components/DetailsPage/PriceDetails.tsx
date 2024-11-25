import React from "react";
import { Descriptions } from "antd";
import type { DescriptionsProps } from "antd";
import { PriceBreakdown } from "../../interfaces/PriceBreakdown";

interface PriceBreakdownProps {
  price: PriceBreakdown;
}

const PriceDetails: React.FC<PriceBreakdownProps> = ({ price }) => {
  const items: DescriptionsProps["items"] = [
    {
      key: "1",
      label: "Base Price",
      children: `$ ${price.basePrice}`,
    },

    {
      key: "2",
      label: "Fees",
      children: `$ ${price.fees}`,
    },
    {
      key: "3",
      label: "Total Price",
      children: `$ ${price.totalPrice}`,
    },
  ];
  return (
    <Descriptions
      title="Price Breakdown"
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

export default PriceDetails;
