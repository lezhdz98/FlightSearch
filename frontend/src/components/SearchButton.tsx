import React from "react";
import { SearchOutlined } from "@ant-design/icons";
import { Button } from "antd";
import { useNavigate } from "react-router-dom";

const SearchButton: React.FC = () => {
  const navigate = useNavigate();

  const handleNewSearch = () => {
    navigate("/");
  };

  return (
    <>
      <Button
        type="primary"
        icon={<SearchOutlined />}
        onClick={handleNewSearch}
      >
        New Search
      </Button>
    </>
  );
};

export default SearchButton;
