import React, { useEffect, useState } from "react";
import { Descriptions, Typography } from "antd";
import type { DescriptionsProps } from "antd";
import { Flight } from "../interfaces/Flight";
import dayjs from "dayjs";
import duration from "dayjs/plugin/duration";
import { FlightRequest } from "../interfaces/FlightRequest";

interface FlightProps {
  flight: Flight;
  flightRequest: FlightRequest;
}

dayjs.extend(duration);
const GeneralInfo: React.FC<FlightProps> = ({ flight, flightRequest }) => {
  const { Title } = Typography;

  const [departureAirport, setDepartureAirport] = useState("");
  const [arrivalAirport, setArrivalAirport] = useState("");

  useEffect(() => {
    // Search departure airport
    const departureSegment = flight.segments.find(
      (segment) => segment.departureAirport.code === flightRequest.origin
    );

    // Search arrival airport
    const arrivalSegment = flight.segments.find(
      (segment) => segment.arrivalAirport.code === flightRequest.destination
    );

    setDepartureAirport(
      departureSegment
        ? departureSegment.departureAirport.name
        : flightRequest.origin
    );
    setArrivalAirport(
      arrivalSegment
        ? arrivalSegment.arrivalAirport.name
        : flightRequest.destination
    );
  }, [flight, flightRequest]);

  const items: DescriptionsProps["items"] = [
    {
      key: "1",
      label: "Total Time",
      span: "filled",
      children: `${dayjs.duration(flight.totalFlightTime).format("HH:mm")} Hrs`,
    },
    {
      key: "2",
      label: "Departure Airport",
      children: `${departureAirport} (${flightRequest.origin})`,
    },
    {
      key: "3",
      label: "Arrival Airport",
      children: `${arrivalAirport} 
        (${flightRequest.destination})`,
    },
  ];

  return (
    <>
      <Title level={2}>Flight {flight.id}:</Title>
      <Descriptions
        items={items}
        column={2}
        style={{
          maxWidth: "75%",
          textAlign: "left",
          fontSize: 20,
          padding: "30px",
        }}
      ></Descriptions>
    </>
  );
};

export default GeneralInfo;
