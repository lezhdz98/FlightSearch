import { Button, Checkbox, DatePicker, Form, Input, Select } from "antd";
import { useAirport } from "../hooks/useAirport";
import { fetchAirports } from "../services/airportService";
import { fetchFlights } from "../services/flightSearchService";
import dayjs, { Dayjs } from "dayjs";
import { FlightRequest } from "../interfaces/FlightRequest";
import { useFlight } from "../hooks/useFlight";
import { useNavigate } from "react-router-dom";
import { useFlightRequest } from "../hooks/useFlightRequest";

const SearchForm: React.FC = () => {
  const { airports, setAirports, loading, setLoading, setError } = useAirport();

  const { setFlights } = useFlight();

  const { setFlightRequest } = useFlightRequest();

  const [form] = Form.useForm();

  const navigate = useNavigate();

  const handleSearch = async (value: string) => {
    if (value) {
      setLoading(true);
      try {
        const data = await fetchAirports(value);
        setAirports(data);
      } catch (error) {
        setError("Error fetching airports");
      } finally {
        setLoading(false);
      }
    } else {
      setAirports([]);
    }
  };

  const disabledDepartureDate = (current: Dayjs) => {
    return current && current < dayjs().startOf("day");
  };

  const disabledReturnDate = (current: Dayjs) => {
    const departureDate = form.getFieldValue("departureDate");
    if (!departureDate) {
      return current && current < dayjs().startOf("day");
    }
    return current && current < departureDate.startOf("day");
  };

  const onFinish = async (values: FlightRequest) => {
    console.log("Form values:", values);
    try {
      const data = await fetchFlights(values);
      setFlights(data);
      setFlightRequest(values);
      console.log("Fetched flights:", data);
      navigate("/results-page");
    } catch (error) {
      console.log("Error fetching airports");
    }
  };

  return (
    <>
      <Form
        labelCol={{ span: 50 }}
        wrapperCol={{ offset: 1, span: 50 }}
        layout="vertical"
        style={{ maxWidth: "100%" }}
        initialValues={{ nonStop: false }}
        form={form}
        onFinish={onFinish}
      >
        <Form.Item label="Departure Airport" name="origin" required>
          <Select
            showSearch
            placeholder="Type to search airports"
            onSearch={handleSearch}
            loading={loading}
            notFoundContent={loading ? <p>Loading...</p> : null}
          >
            {airports.map((airport) => (
              <Select.Option key={airport.code} value={airport.code}>
                {airport.name} ({airport.city})
              </Select.Option>
            ))}
          </Select>
        </Form.Item>

        <Form.Item label="Arrival Airport" name="destination" required>
          <Select
            showSearch
            placeholder="Type to search airports"
            onSearch={handleSearch}
            loading={loading}
            notFoundContent={loading ? <p>Loading...</p> : null}
          >
            {airports.map((airport) => (
              <Select.Option key={airport.code} value={airport.code}>
                {airport.name} ({airport.city})
              </Select.Option>
            ))}
          </Select>
        </Form.Item>

        <Form.Item
          label="Departure Date"
          name="departureDate"
          rules={[
            {
              required: true,
              message: "Please select a departure date",
            },
          ]}
        >
          <DatePicker disabledDate={disabledDepartureDate} />
        </Form.Item>
        <Form.Item
          label="Return Date"
          name="returnDate"
          dependencies={["departureDate"]}
        >
          <DatePicker disabledDate={disabledReturnDate} />
        </Form.Item>

        <Form.Item
          label="Number of Adults"
          name="adults"
          rules={[{ required: true, message: "Number of adults is required" }]}
        >
          <Input type="number" min={1} />
        </Form.Item>

        <Form.Item label="Currency" name="currencyCode" required>
          <Select>
            <Select.Option value="USD">USD</Select.Option>
            <Select.Option value="MXN">MXN</Select.Option>
            <Select.Option value="EUR">EUR</Select.Option>
          </Select>
        </Form.Item>

        <Form.Item name="nonStop" valuePropName="checked">
          <Checkbox>Non-stop</Checkbox>
        </Form.Item>

        <Form.Item wrapperCol={{ offset: 6, span: 16 }}>
          <Button type="primary" htmlType="submit">
            Search
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default SearchForm;
