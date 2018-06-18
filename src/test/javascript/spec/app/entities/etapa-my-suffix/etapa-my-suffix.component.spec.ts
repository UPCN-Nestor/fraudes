/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { EtapaMySuffixComponent } from '../../../../../../main/webapp/app/entities/etapa-my-suffix/etapa-my-suffix.component';
import { EtapaMySuffixService } from '../../../../../../main/webapp/app/entities/etapa-my-suffix/etapa-my-suffix.service';
import { EtapaMySuffix } from '../../../../../../main/webapp/app/entities/etapa-my-suffix/etapa-my-suffix.model';

describe('Component Tests', () => {

    describe('EtapaMySuffix Management Component', () => {
        let comp: EtapaMySuffixComponent;
        let fixture: ComponentFixture<EtapaMySuffixComponent>;
        let service: EtapaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [EtapaMySuffixComponent],
                providers: [
                    EtapaMySuffixService
                ]
            })
            .overrideTemplate(EtapaMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtapaMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtapaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EtapaMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.etapas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
