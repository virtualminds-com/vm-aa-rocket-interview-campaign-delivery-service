# Coding task: partner format change

**Time:** about 35 minutes in total, including a short walkthrough at the end.
You won't be judged on finishing everything. We care more about how you approach the change, the quality of
what you do write, and how you explain your decisions.

## Context

`campaign-delivery-service` exposes daily delivery statistics for advertising campaigns. Today its only consumer
is our **internal reporting dashboard**, which reads `GET /campaigns/{campaignId}/delivery`.

Read `README.md` for how the project is built and run.

## The ticket

> **DELIV-482: Expose delivery data for the Adlantic DSP integration**
>
> We're onboarding Adlantic, a DSP partner. Their ingestion job will pull delivery data for a campaign from our
> service. Their integration spec requires:
>
> 1. **Spend as micros:** an integer where 1,000,000 micros = 1 unit of currency (12.34 → `12340000`).
> 2. **An explicit currency** as an ISO-4217 code (`EUR`, `USD`, …).
> 3. **Dates in ISO-8601** (`yyyy-MM-dd`).
>
> The reporting dashboard team can't ship any changes this sprint, so the dashboard **must keep working**.

## What we'd like you to do

1. **Get oriented (about 5–7 min).** Run the project, open Swagger UI, call the endpoint, run the tests.
   Be ready to briefly explain how a request flows through the code.
2. **Implement the change (about 15–20 min)** so that Adlantic can get data in their format and the dashboard
   keeps working.
3. **Test it.** Add or adjust tests so you'd be confident merging this.
4. **Walk us through it (about 5–10 min).** How you introduced the change, how it's tested, what trade-offs
   you made, and what you'd still do before this reaches production.

## How we work

- **Questions are welcome.** Your interviewer plays the product owner and can answer questions about the
  requirements. Asking is a normal part of the job here.
- **If you make an assumption, write it down** in a short `NOTES.md`, the way you would in a PR description.
- **Commit as you go**, with messages you'd be happy to see in our history.
- **Tooling:** you may use your IDE, documentation and web search. AI coding assistants:
  **[INTERVIEWER: state allowed / not allowed before the session]**.

Good luck, and think out loud as much as you're comfortable with.
