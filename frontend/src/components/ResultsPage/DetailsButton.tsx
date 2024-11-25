import { UnorderedListOutlined } from "@ant-design/icons";
import { Button } from "antd";
import { useNavigate } from "react-router-dom";
import { Flight } from "../../interfaces/Flight";

interface DetailsButtonProps {
  flight: Flight;
}

const DetailsButton: React.FC<DetailsButtonProps> = ({ flight }) => {
  const navigate = useNavigate();

  const handleDetailsPage = () => {
    navigate(`/details-flight/${flight.id}`, { state: { flight } });
  };

  return (
    <>
      <Button
        type="primary"
        icon={<UnorderedListOutlined />}
        iconPosition="end"
        onClick={handleDetailsPage}
      >
        Flight Details
      </Button>
    </>
  );
};

export default DetailsButton;
