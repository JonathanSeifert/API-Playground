import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";
import EntryList from "../EntryList"; 

//Cleanup
let container = null;
beforeEach(() => {
  // setup a DOM element as a render target
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  // cleanup on exiting
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});


describe('EntryList', () => {
  test('renders without crashing', async () => {
    render(<EntryList/>, container);
  })
  it('fetches user data', async () => {
    const fakeData = [{id: 1, name: "Tom"}];
    jest.spyOn(global, 'fetch').mockImplementation(() =>
      Promise.resolve({
        json: () => Promise.resolve(fakeData)
      }) 
    );
    // Use the asynchronous version of act to apply resolved promises
    await act(async () => {
    render(<EntryList />, container);
    });
    expect(container.querySelector("td.id-col").textContent).toBe("1");
    expect(container.querySelector("td.name-col").textContent).toBe("Tom");
    expect(container.querySelector("#message-el").textContent).toBe("");
    // remove the mock to ensure tests are completely isolated
    global.fetch.mockRestore();
  })
  it('fetches no user data', async () => {
    const fakeData = [];
    jest.spyOn(global, 'fetch').mockImplementation(() => 
      Promise.resolve({
        json: () => Promise.resolve(fakeData)
      })
    )
    await act(async () => {
      render(<EntryList />, container);
    });
    expect(container.querySelector("td.id-col")).toBeNull();
    expect(container.querySelector("td.name-col")).toBeNull();
    global.fetch.mockRestore();
  })
  
})

