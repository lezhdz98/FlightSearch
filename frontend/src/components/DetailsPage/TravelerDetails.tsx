import React from "react";
import { Descriptions, Table, Typography } from "antd";
import type { DescriptionsProps } from "antd";
import { TravelerPricing } from "../../interfaces/TravelerPricing";
import { ColumnsType } from "antd/es/table";

interface TravelerProps {
  traveler: TravelerPricing;
}

const TravelerDetails: React.FC<TravelerProps> = ({ traveler }) => {
  const { Title } = Typography;
  const items: DescriptionsProps["items"] = [
    {
      key: "1",
      label: "Cabin",
      children: traveler.cabin,
    },

    {
      key: "2",
      label: "Class",
      children: traveler.flightClass,
    },
    {
      key: "3",
      label: "Price per Traveler",
      children: `$ ${traveler.totalPrice}`,
    },
  ];

  // Define columns for amenities
  const columns: ColumnsType<any> = [
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Chargeable",
      dataIndex: "chargeable",
      key: "chargeable",
    },
  ];

  // data for the table
  const data = traveler.amenities.map((amenity, index) => ({
    key: index,
    name: amenity.name,
    chargeable: `${amenity.chargeable}`,
  }));

  return (
    <div>
      <Descriptions
        title={`Traveler ${traveler.id}:`}
        items={items}
        column={3}
        style={{ maxWidth: "75%", textAlign: "left" }}
      ></Descriptions>
      <Title level={5}>Amenities: </Title>
      <Table
        columns={columns}
        dataSource={data}
        pagination={false}
        style={{ maxWidth: "90%", margin: "20px" }}
      />
    </div>
  );
};

export default TravelerDetails;
