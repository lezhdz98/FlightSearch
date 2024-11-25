import { DoubleLeftOutlined } from "@ant-design/icons";
import { Button } from "antd";
import { useNavigate } from "react-router-dom";

const ResultsButton: React.FC = () => {
  const navigate = useNavigate();

  const handleResultsPage = () => {
    navigate("/results-page");
  };

  return (
    <>
      <Button
        type="primary"
        icon={<DoubleLeftOutlined />}
        iconPosition="start"
        onClick={handleResultsPage}
      >
        Flight Details
      </Button>
    </>
  );
};

export default ResultsButton;
