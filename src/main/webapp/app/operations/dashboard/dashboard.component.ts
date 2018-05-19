import { Component, OnInit, OnDestroy } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { EventService } from '../event/event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
// import { Chart } from 'chart.js';
import 'chartjs-plugin-annotation';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
}) export class DashboardComponent implements OnInit, OnDestroy {

    chart: any[];
    CVs: any[];
    events: any[];
    chartDataAux: any[];
    chartOptions: any;
    chartAnnotations: any[];
    today: Date;
    last: Date;
    page: any;
    itemsPerPage: number;
    pageSettings: any;
    loaded: boolean;

    constructor(
        public cvService: CVService,
        public eventService: EventService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.chart = [];
        this.CVs = [];
        this.events = [];
        this.chartDataAux = [];
        this.chartOptions = {};
        this.chartAnnotations = [];
        this.today = new Date();
        this.last = new Date(this.today.getTime() - (7 * 24 * 60 * 60 * 1000));
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.loaded = false;
    }

    ngOnInit() {
        this.cvService.dates(this.last.toISOString(), this.pageSettings).subscribe(
            (data: any) => this.onSuccessCV(data.json(), data.headers),
            (error: Response) => this.onError(error)
        );
        this.chartOptions = {
            responsive: true,
            legend: {
                display: true
            },
            scales: {
                xAxes: [{
                    id: 'x-axis-0',
                    display: true,
                    type: 'time',
                    time: {
                        unit: 'day',
                        unitStepSize: 1,
                        min: this.dateToNumber(new Date(this.last)),
                        displayFormats: {
                            day: 'll'
                        }
                    }
                }],
                yAxes: [{
                    id: 'y-axis-0',
                    type: 'logarithmic',
                    display: true,
                    min: 0,
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            tooltips: {
                callbacks: {
                    title: function(tooltipItem, data) {
                        // Removing milliseconds from the xLabel
                        let s_date = tooltipItem[0].xLabel;
                        const i = s_date.lastIndexOf('.');
                        const j = s_date.lastIndexOf(' ');

                        // Constructing the new date string
                        s_date = s_date.substring(0, i) + s_date.substring(j, s_date.length);
                        const showDate = new Date(s_date).toUTCString();

                        return showDate.substring(0, showDate.length - 4);
                    },
                    label: function(tooltipItem, data) {
                        let label = '' + data.datasets[tooltipItem.datasetIndex].label || '';

                        if (label) {
                            label += ': ';
                        }

                        label += tooltipItem.yLabel;
                        return label;
                    }
                }
            },
            annotation: {
                annotations: this.chartAnnotations
            }
        };
        /*
        const canvas: any = document.getElementById('myChart');
        const ctx = canvas.getContext('2d');
        this.chart = new Chart(ctx, {
            type: this.chartType,
            data: {
                datasets: this.CVs
            },
            options: this.chartOptions
        });
        */
    }

    ngOnDestroy() { }

    onSuccessCV(data: any, headers: any) {
        for (let i = 0; i < data.content.length; i++) {
            const dataToInsert: any[] = [];
            for (let j = 0; j < data.content[i]['controlVarEntries'].length; j++) {
                const date: Date = new Date(data.content[i]['controlVarEntries'][j]['creationMoment']);
                date.setHours(date.getHours() + 2);
                dataToInsert.push({
                    x: this.dateToNumber(date),
                    y: data.content[i]['controlVarEntries'][j]['value']
                });
            }
            const dato: any = {
                data: dataToInsert,
                label: data.content[i]['name'],
                backgroundColor: data.content[i]['extractor']['style']['backgroundColor'],
                fill: false,
                showLine: true
            }
            this.CVs.push(dato);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
        this.eventService.dates(this.last.toISOString(), this.today.toISOString(), this.pageSettings).subscribe(
            (res: ResponseWrapper) => this.onSuccessEvent(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    onSuccessEvent(data: any, headers: any) {
        for (let i = 0; i < data.length; i++) {
            const startingDate: any = data[i]['startingDate'];
            const endingDate: any = data[i]['endingDate'];
            let type: string;
            let value, xMin, xMax: number;
            if (endingDate === null) {
                type = 'line';
                value = this.dateToNumber(new Date(startingDate))
            } else {
                type = 'box';
                xMin = this.dateToNumber(new Date(startingDate));
                if (this.dateToNumber(new Date(endingDate)) > this.dateToNumber(new Date(this.today))) {
                    xMax = this.dateToNumber(new Date(this.today));
                } else {
                    xMax = this.dateToNumber(new Date(endingDate));
                }
            }
            const chartAnnotation: any = {
                borderColor: 'red',
                mode: 'vertical',
                type: type,
                value: value,
                xMin: xMin,
                xMax: xMax,
                scaleID: 'x-axis-0',
                xScaleID: 'x-axis-0',
                label: {
                    fontFamily: 'roboto',
                    enabled: true,
                    content: data[i]['name']
                }
            }
            this.chartAnnotations.push(chartAnnotation);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
        this.loaded = true;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    private dateToNumber(date: Date) {
        return date.getTime();
    }

}
