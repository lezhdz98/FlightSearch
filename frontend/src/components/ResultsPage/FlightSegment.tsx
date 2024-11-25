import React from "react";
import { Descriptions, Table, Typography } from "antd";
import type { DescriptionsProps } from "antd";
import { FlightSegment as FlightSegmentType } from "../../interfaces/FlightSegment";
import dayjs from "dayjs";
import { ColumnsType } from "antd/es/table";

interface FlightSegmentProps {
  segment: FlightSegmentType;
}

const FlightSegment: React.FC<FlightSegmentProps> = ({ segment }) => {
  const { Title } = Typography;
  const items: DescriptionsProps["items"] = [
    {
      key: "1",
      label: "Departure Date and time",
      children: dayjs(segment.departureDateTime).format(
        "DD-MM-YYYY [at] HH:mm"
      ),
    },
    {
      key: "2",
      label: "Arrival Date and time",
      children: dayjs(segment.arrivalDateTime).format("DD-MM-YYYY [at] HH:mm"),
    },
    {
      key: "3",
      label: "Departure Airport",
      children: `${segment.departureAirport.name} (${segment.departureAirport.code})`,
    },
    {
      key: "4",
      label: "Arrival Airport",
      children: `${segment.arrivalAirport.name} - (${segment.arrivalAirport.code})`,
    },
    {
      key: "5",
      label: "Airline",
      span: "filled",
      children: `${segment.airline.name} (${segment.airline.code})`,
    },
  ];

  // define data columns of the stops
  const columns: ColumnsType<any> = [
    {
      title: "Airport",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Duration",
      dataIndex: "duration",
      key: "duration",
    },
  ];

  // data for the table
  const data = segment.stops.map((stop, index) => ({
    key: index,
    name: `${stop.Airport.name}  (${stop.Airport.code})`,
    duration: `${dayjs.duration(stop.duration).format("HH:mm")} Hrs`,
  }));

  return (
    <div
      style={{
        maxWidth: "75%",
        textAlign: "left",
        fontSize: 20,
        border: "1px solid black",
        padding: "20px",
      }}
    >
      <Descriptions
        title={`Segment ${segment.id}`}
        items={items}
        column={2}
      ></Descriptions>
      <Title level={5}>Stops: </Title>
      <Table
        columns={columns}
        dataSource={data}
        pagination={false}
        style={{ maxWidth: "90%", margin: "20px" }}
      />
    </div>
  );
};
export default FlightSegment;
