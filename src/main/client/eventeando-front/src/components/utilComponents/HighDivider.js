import React from "react";
import {Divider, Header} from "semantic-ui-react";

export function HighDivider(Icon, HeaderTitle, Body) {
  return <React.Fragment>
    <Divider horizontal>
      <Header>
        {Icon}
        {HeaderTitle}
      </Header>
    </Divider>
    {Body}
  </React.Fragment>;
}