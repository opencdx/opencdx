/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 */

import React from 'react';

//const iframeSrc = "https://localhost:8761/";

function IframeNats({href}) {


  // useEffect(() => {
  //   let iframe = document.querySelector('iframe[name="prod"]');
  //   var elmnt = iframe.contentWindow.document.getElementsByTagName("H1")[0];
  //   elmnt.style.display = "none";

  //   // let ele = iframe.contentWindow.document.querySelector("nav")
  // });
 console.log(href)
 
  return (
    // <div className="font-display text-xl text-primary dark:text-primary-dark leading-relaxed">
    //   {children}
    // </div>
    <div>

      <iframe  src={href}
        width="100%"
        height="800"
        frameBorder="0"
        allowFullScreen
        name='prod'
       >
      
    </iframe>
{/* <Iframes url={iframeSrc}
        width="100%"
        height="600px"
        id=""
        className="iframe"
        styles={{marginTop: "-25px"}}
        position="relative"/> */}
    {/* <iframe
        src={iframeSrc}
        width="100%"
        height="600"
        frameBorder="0"
        allowFullScreen
        title="Embedded Website"
        style={{marginTop: -50 + 'px'}}
    /> */}
</div>
  );
}

export default IframeNats;
